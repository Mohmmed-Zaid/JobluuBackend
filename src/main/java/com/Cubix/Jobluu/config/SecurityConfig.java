package com.Cubix.Jobluu.config;

import com.Cubix.Jobluu.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // Authentication endpoints - Public access
                                "/api/auth/**",
                                "/api/public/**",
                                "/api/users/register",
                                "/api/users/login",
                                "/api/users/sendOTP/**",     
                                "/api/users/verifyOtp/**",   
                                "/api/users/changePass",
                                
                                // Job endpoints - Public read access (typically job listings should be public)
                                "/jobs/getAll",
                                "/jobs/search",
                                "/jobs/status/**",
                                "/jobs/{id}",
                                
                                // Static file serving for uploaded logos
                                "/uploads/**",
                                
                                // Documentation and monitoring
                                "/v3/api-docs/**", 
                                "/swagger-ui/**", 
                                "/swagger-ui.html",
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers(
                                // Job management endpoints - Authenticated access required
                                "/jobs/post",
                                "/jobs/create-with-logo",
                                "/jobs/update/**",
                                "/jobs/delete/**",
                                "/jobs/update-with-logo/**",
                                
                                // Profile management - Authenticated access required
                                "/api/profiles/**",
                                
                                // Notification management - Authenticated access required
                                "/notification/**",
                                
                                // User account management - Authenticated access required
                                "/api/users/**"
                        ).authenticated()
                        .anyRequest().authenticated()
                );
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of(
        "http://localhost:5173",          
        "https://jobluuui.onrender.com"   
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
