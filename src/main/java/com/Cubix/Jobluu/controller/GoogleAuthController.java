// GoogleAuthController.java - Updated version with better error handling
package com.Cubix.Jobluu.controller;

import com.Cubix.Jobluu.dto.AccountType;
import com.Cubix.Jobluu.dto.GoogleLoginRequest;
import com.Cubix.Jobluu.dto.UserDto;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.jwt.JwtHelper;
import com.Cubix.Jobluu.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}, allowCredentials = "true")
public class GoogleAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @Value("${google.client-id}")
    private String googleClientId;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            log.info("Received Google login request for account type: {}", request.getAccountType());

            if (request.getCredential() == null || request.getCredential().trim().isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Google credential is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            if (request.getAccountType() == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Account type is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Verify Google ID token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getCredential());

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String googleId = payload.getSubject();
                String picture = (String) payload.get("picture");
                Boolean emailVerified = payload.getEmailVerified();

                log.info("Google token verified successfully for email: {}", email);

                if (!emailVerified) {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Email not verified by Google");
                    return ResponseEntity.status(400).body(errorResponse);
                }

                // Check if user exists
                UserDto existingUser = null;
                try {
                    existingUser = userService.getUserByEmail(email);
                    log.info("Found existing user for email: {}", email);
                } catch (Exception e) {
                    log.info("No existing user found for email: {}, will create new user", email);
                }

                UserDto user;
                if (existingUser != null) {
                    // Update existing user with Google info if needed
                    if (existingUser.getGoogleId() == null || existingUser.getGoogleId().isEmpty()) {
                        existingUser.setGoogleId(googleId);
                        if (picture != null) {
                            existingUser.setProfilePicture(picture);
                        }
                        user = userService.updateUser(existingUser);
                        log.info("Updated existing user with Google info: {}", email);
                    } else {
                        user = existingUser;
                        log.info("Using existing Google user: {}", email);
                    }
                } else {
                    // Create new user with Google info
                    try {
                        UserDto newUser = new UserDto();
                        newUser.setName(name);
                        newUser.setEmail(email);
                        newUser.setAccountType(request.getAccountType());
                        newUser.setGoogleId(googleId);
                        newUser.setProfilePicture(picture);
                        newUser.setPassword(""); // No password for Google users

                        user = userService.createGoogleUser(newUser);
                        log.info("Created new Google user: {}", email);
                    } catch (JobluuException e) {
                        if ("USER_FOUND".equals(e.getMessage())) {
                            // Race condition - user was created by another request
                            user = userService.getUserByEmail(email);
                            log.info("User was created concurrently, using existing user: {}", email);
                        } else {
                            throw e;
                        }
                    }
                }

                // Generate JWT token
                String jwt = jwtHelper.generateToken(email);
                log.info("Generated JWT token for user: {}", email);

                // Create response
                Map<String, Object> response = new HashMap<>();
                response.put("token", jwt);
                response.put("user", user);
                response.put("message", "Google login successful");

                return ResponseEntity.ok(response);

            } else {
                log.error("Invalid Google token received");
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid Google token");
                return ResponseEntity.status(401).body(errorResponse);
            }

        } catch (Exception e) {
            log.error("Google authentication failed: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Google authentication failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}