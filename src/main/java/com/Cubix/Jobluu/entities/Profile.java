package com.Cubix.Jobluu.entities;

import com.Cubix.Jobluu.dto.Certifications;
import com.Cubix.Jobluu.dto.Experience;
import com.Cubix.Jobluu.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profiles")
public class Profile {

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

    public ProfileDto toDTO() {
        return new ProfileDto(
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
