package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.controller.GoogleAuthController;
import com.Cubix.Jobluu.dto.*;
import com.Cubix.Jobluu.entities.OTP;
import com.Cubix.Jobluu.entities.User;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.repositories.NotificationRepository;
import com.Cubix.Jobluu.repositories.OTPRepository;
import com.Cubix.Jobluu.repositories.UserRepository;
import com.Cubix.Jobluu.utility.Data;
import com.Cubix.Jobluu.utility.Utilities;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.Cubix.Jobluu.jwt.JwtAuthenticationFilter.log;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Value("${google.client-id}")
    private String googleClientId;

    @Override
    public UserDto registerUser(UserDto userDto) throws JobluuException {
        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());
        if (optional.isPresent()) {
            throw new JobluuException("USER_FOUND");
        }

        // Fixed: Now works because createProfile returns ProfileDto
        ProfileDto profile = profileService.createProfile(userDto.getEmail());
        userDto.setProfileId(profile.getId().toString());
        userDto.setId(Utilities.getNextSequence("users"));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = userDto.toEntity();
        user = userRepository.save(user);
        return user.toDto();
    }

    @Override
    public UserDto getUserByEmail(String email) throws JobluuException {
        return userRepository.findByEmail(email).orElseThrow(() -> new JobluuException("USER_NOT_FOUND")).toDto();
    }

    @Override
    public UserDto loginUser(LoginDto loginDto) throws JobluuException {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new JobluuException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new JobluuException("INVALID_CREDENTIAL");
        }

        return user.toDto();
    }

    @Override
    public ResponseDto changePassword(LoginDto loginDto) throws JobluuException {
        String email = loginDto.getEmail();
        String newPassword = loginDto.getPassword();

        if (email == null || email.trim().isEmpty()) {
            throw new JobluuException("EMAIL_REQUIRED");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new JobluuException("PASSWORD_REQUIRED");
        }
        if (newPassword.length() < 6) {
            throw new JobluuException("PASSWORD_TOO_SHORT");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobluuException("USER_NOT_FOUND"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        NotificationDTO notificication = new NotificationDTO();
        notificication.setId(user.getId());
        notificication.setMessage("Password has Reset Successfully");
        notificication.setAction("Password Reset");
        notificationService.sendNotification(notificication);
        return new ResponseDto("PASSWORD_CHANGED_SUCCESSFULLY", true);
    }

    @Override
    public Boolean sendOTP(String email) throws Exception {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new JobluuException("USER_NOT_FOUND"));

        String genOtp = Utilities.generateOTP();
        OTP otp = new OTP(email, genOtp, LocalDateTime.now());
        otpRepository.save(otp);

        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true);
        message.setTo(email);
        message.setSubject("Verify your Jobluu Account");
        message.setText(Data.getMessageBody(email, genOtp), true);

        mailSender.send(mm);
        return true;
    }

    @Override
    public Boolean verifyOTP(String email, String otp) throws JobluuException {
        OTP otpEntity = otpRepository.findById(email)
                .orElseThrow(() -> new JobluuException("OTP_NOT_FOUND"));

        if (!otpEntity.getOtpCode().equals(otp)) {
            throw new JobluuException("OTP_INCORRECT");
        }

        return true;
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTP() {
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOTP = otpRepository.findByCreationTimeBefore(expiry);
        if (!expiredOTP.isEmpty()) {
            otpRepository.deleteAll(expiredOTP);
            System.out.println("Removed " + expiredOTP.size() + " expired OTP(s).");
        }
    }

    @Override
    public UserDto createGoogleUser(UserDto userDto) throws JobluuException {
        try {
            // Synchronized block to prevent race conditions
            synchronized (this) {
                // Double-check if user exists
                Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
                if (existingUser.isPresent()) {
                    log.info("User already exists for email: {}, returning existing user", userDto.getEmail());
                    return existingUser.get().toDto();
                }

                // Create profile first
                ProfileDto profile = profileService.createProfile(userDto.getEmail());
                userDto.setProfileId(profile.getId().toString());
                userDto.setId(Utilities.getNextSequence("users"));

                // No password encoding for Google users
                userDto.setPassword(""); // Empty string for Google users

                User saved = userRepository.save(userDto.toEntity());
                log.info("Created new Google user: {}", userDto.getEmail());

                return saved.toDto();
            }
        } catch (Exception e) {
            log.error("Error creating Google user: ", e);
            // If user already exists due to race condition, return existing user
            Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
            if (existingUser.isPresent()) {
                log.info("Returning existing user due to race condition: {}", userDto.getEmail());
                return existingUser.get().toDto();
            }
            throw new JobluuException("Failed to create Google user: " + e.getMessage());
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws JobluuException {
        try {
            Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
            if (existingUser.isPresent()) {
                User user = existingUser.get();

                // Update fields
                if (userDto.getName() != null) {
                    user.setName(userDto.getName());
                }
                if (userDto.getGoogleId() != null) {
                    user.setGoogleId(userDto.getGoogleId());
                }
                if (userDto.getProfilePicture() != null) {
                    user.setProfilePicture(userDto.getProfilePicture());
                }

                User saved = userRepository.save(user);
                return saved.toDto();
            } else {
                throw new JobluuException("User not found for update");
            }
        } catch (Exception e) {
            log.error("Error updating user: ", e);
            throw new JobluuException("Failed to update user: " + e.getMessage());
        }
    }
}
