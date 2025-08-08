package com.Cubix.Jobluu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    private Long userId;
    private String userName;
    private String email;
    private String resumeUrl;
    private LocalDateTime appliedAt;
    private String status; // APPLIED, REVIEWED, SHORTLISTED, REJECTED, HIRED
}
