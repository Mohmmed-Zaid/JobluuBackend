package com.Cubix.Jobluu.entities;

import com.Cubix.Jobluu.dto.Applicant;
import com.Cubix.Jobluu.dto.JobDTO;
import com.Cubix.Jobluu.dto.JobStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jobs")
public class Job {
    @Id
    private Long id;
    private String jobTitle;
    private String company;
    private String companyLogo;
    private List<Applicant> applicants; // Fixed field name
    private String about;
    private String experience; // Fixed spelling
    private String jobType;
    private String location;
    private Long packageOffered;
    private LocalDateTime postTime;
    private String description;
    private List<String> skillsRequired;
    private JobStatus jobStatus;

    public JobDTO toDTO() {
        return new JobDTO(
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