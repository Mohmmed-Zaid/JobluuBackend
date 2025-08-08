package com.Cubix.Jobluu.repositories;

import com.Cubix.Jobluu.entities.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OTPRepository extends MongoRepository<OTP,String> {
    List<OTP> findByCreationTimeBefore(LocalDateTime expiry);
}
