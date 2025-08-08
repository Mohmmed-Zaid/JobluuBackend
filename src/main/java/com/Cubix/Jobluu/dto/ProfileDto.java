package com.Cubix.Jobluu.dto;

import com.Cubix.Jobluu.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    @Id
    private Long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;

    private List<String> skills;
    private List<Experience> experiences;
    private List<Certifications> certifications;

    public Profile toEntity() {
        return new Profile(
                this.id,
                this.email,
                this.jobTitle,
                this.company,
                this.location,
                this.about,
                this.skills,
                this.experiences,
                this.certifications
        );
    }
}
