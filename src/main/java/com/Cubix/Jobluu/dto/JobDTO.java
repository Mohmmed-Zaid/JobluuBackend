package com.Cubix.Jobluu.dto;

import com.Cubix.Jobluu.entities.Job;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private Long id;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Company name is required")
    private String company;

    private String companyLogo;
    private List<Applicant> applicants; // Fixed field name
    private String about;
    private String experience; // Fixed spelling

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Package offered must be positive")
    private Long packageOffered;

    private LocalDateTime postTime;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Skills required cannot be null")
    private List<String> skillsRequired;

    private JobStatus jobStatus;

    public Job toEntity() {
        return new Job(
                this.id,
                this.jobTitle,
                this.company,
                this.companyLogo,
                this.applicants,
                this.about,
                this.experience,
                this.jobType,
                this.location,
                this.packageOffered,
                this.postTime,
                this.description,
                this.skillsRequired,
                this.jobStatus
        );
    }
}