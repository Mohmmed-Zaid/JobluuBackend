package com.Cubix.Jobluu.controller;

import com.Cubix.Jobluu.dto.JobDTO;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    private final String UPLOAD_DIR = "uploads/logos/";

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO) throws JobluuException {
        return new ResponseEntity<>(jobService.postJob(jobDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) throws JobluuException {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable Long id,
            @RequestBody @Valid JobDTO jobDTO) throws JobluuException {
        return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJobById(id);
            return ResponseEntity.ok("Job deleted successfully with ID: " + id);
        } catch (JobluuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found with ID: " + id);
        }
    }

    @PostMapping("/create-with-logo")
    public ResponseEntity<JobDTO> postJobWithLogo(
            @RequestPart("job") @Valid JobDTO jobDTO,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) throws JobluuException {
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                String logoUrl = saveLogoFile(logoFile);
                jobDTO.setCompanyLogo(logoUrl);
            } catch (IOException e) {
                throw new JobluuException("Failed to save logo file: " + e.getMessage());
            }
        }
        return new ResponseEntity<>(jobService.postJob(jobDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-with-logo/{id}")
    public ResponseEntity<JobDTO> updateJobWithLogo(
            @PathVariable Long id,
            @RequestPart("job") @Valid JobDTO jobDTO,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) throws JobluuException {
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                String logoUrl = saveLogoFile(logoFile);
                jobDTO.setCompanyLogo(logoUrl);
            } catch (IOException e) {
                throw new JobluuException("Failed to save logo file: " + e.getMessage());
            }
        }
        return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobDTO>> getJobsByStatus(@PathVariable String status) {
        List<JobDTO> jobs = jobService.getJobsByStatus(status);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam("q") String query) {
        List<JobDTO> jobs = jobService.searchJobs(query);
        return ResponseEntity.ok(jobs);
    }

    // Helper method for saving logo files
    private String saveLogoFile(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath);

        return "/uploads/logos/" + uniqueFilename;
    }
}