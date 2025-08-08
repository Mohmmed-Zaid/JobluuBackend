package com.Cubix.Jobluu.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {

    @Value("${jwt.secret:jobluuSuperSecretKeyForJWTGeneration1234567890}")
    private String SECRET_KEY;

    // INCREASED TO 24 HOURS - This was your main problem (was only 1 hour)
    @Value("${jwt.expiration:86400000}")
    private long JWT_EXPIRATION_MS = 1000 * 60 * 60 * 24; // 24 hours instead of 1 hour

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate JWT Token
    public String generateToken(String username) {
        System.out.println("🔧 JWT Helper - Generating token for user: " + username);
        System.out.println("🔧 JWT Helper - Token expiration: " + JWT_EXPIRATION_MS + "ms (" + (JWT_EXPIRATION_MS / 1000 / 60 / 60) + " hours)");

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("✅ JWT Helper - Token generated successfully");
        return token;
    }

    // Extract username
    public String extractUsername(String token) {
        try {
            String username = extractClaim(token, Claims::getSubject);
            System.out.println("🔧 JWT Helper - Extracted username: " + username);
            return username;
        } catch (Exception e) {
            System.err.println("❌ JWT Helper - Error extracting username: " + e.getMessage());
            throw e;
        }
    }

    // Alternative method name for extracting username
    public String getUsernameFromToken(String token) {
        return extractUsername(token);
    }

    // Generic claim extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean isTokenValid(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            boolean isValid = (extractedUsername.equals(username)) && !isTokenExpired(token);

            System.out.println("🔧 JWT Helper - Token validation:");
            System.out.println("  - Expected username: " + username);
            System.out.println("  - Extracted username: " + extractedUsername);
            System.out.println("  - Is expired: " + isTokenExpired(token));
            System.out.println("  - Is valid: " + isValid);

            return isValid;
        } catch (Exception e) {
            System.err.println("❌ JWT Helper - Token validation error: " + e.getMessage());
            return false;
        }
    }

    // Check if token expired
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            Date now = new Date();
            boolean expired = expiration.before(now);

            System.out.println("🔧 JWT Helper - Token expiry check:");
            System.out.println("  - Current time: " + now);
            System.out.println("  - Token expires: " + expiration);
            System.out.println("  - Is expired: " + expired);

            return expired;
        } catch (Exception e) {
            System.err.println("❌ JWT Helper - Error checking expiration: " + e.getMessage());
            return true; // Consider expired if we can't check
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("❌ JWT Helper - Token expired: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.err.println("❌ JWT Helper - Malformed token: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ JWT Helper - Token parsing error: " + e.getMessage());
            throw e;
        }
    }

    // Add method to get expiration time in milliseconds
    public long getExpirationTime() {
        return JWT_EXPIRATION_MS;
    }

    // Add method to check if token is about to expire (within 1 hour)
    public boolean isTokenNearExpiry(String token) {
        try {
            Date expiration = extractExpiration(token);
            Date now = new Date();
            long timeUntilExpiry = expiration.getTime() - now.getTime();

            // Return true if token expires within 1 hour
            return timeUntilExpiry < (1000 * 60 * 60);
        } catch (Exception e) {
            return true;
        }
    }
}