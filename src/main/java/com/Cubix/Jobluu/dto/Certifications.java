package com.Cubix.Jobluu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certifications {
    private String title;
    private String issue;
    private LocalDateTime issueDate;
    private String certificateId;
}
