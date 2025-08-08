package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.dto.JobDTO;
import com.Cubix.Jobluu.dto.JobStatus;
import com.Cubix.Jobluu.entities.Job;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.repositories.JobRepository;
import com.Cubix.Jobluu.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobDTO postJob(JobDTO jobDTO) throws JobluuException {
        jobDTO.setId(Utilities.getNextSequence("jobs"));
        jobDTO.setPostTime(LocalDateTime.now());
        if (jobDTO.getJobStatus() == null) {
            jobDTO.setJobStatus(JobStatus.ACTIVE); // Set default status
        }
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(Job::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO getJobById(Long id) throws JobluuException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobluuException("JOB_NOT_FOUND"));
        return job.toDTO();
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO updatedJob) throws JobluuException {
        Optional<Job> existingJobOptional = jobRepository.findById(id);
        if (existingJobOptional.isEmpty()) {
            throw new JobluuException("Job not found with id: " + id);
        }

        Job existingJob = existingJobOptional.get();

        // Update fields
        existingJob.setJobTitle(updatedJob.getJobTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setCompany(updatedJob.getCompany());
        existingJob.setCompanyLogo(updatedJob.getCompanyLogo());
        existingJob.setExperience(updatedJob.getExperience()); // Fixed field name
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setPackageOffered(updatedJob.getPackageOffered());
        existingJob.setJobType(updatedJob.getJobType());
        existingJob.setSkillsRequired(updatedJob.getSkillsRequired());
        existingJob.setAbout(updatedJob.getAbout());
        existingJob.setJobStatus(updatedJob.getJobStatus());
        // Don't update postTime for updates - keep original

        return jobRepository.save(existingJob).toDTO();
    }

    @Override
    public void deleteJobById(Long id) throws JobluuException {
        if (!jobRepository.existsById(id)) {
            throw new JobluuException("Job not found with ID: " + id);
        }
        jobRepository.deleteById(id);
    }

    @Override
    public List<JobDTO> getJobsByStatus(String status) {
        try {
            JobStatus jobStatus = JobStatus.valueOf(status.toUpperCase());
            return jobRepository.findByJobStatus(jobStatus)
                    .stream()
                    .map(Job::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid job status: " + status);
        }
    }

    @Override
    public List<JobDTO> searchJobs(String query) {
        return jobRepository.findByJobTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(query, query)
                .stream()
                .map(Job::toDTO)
                .collect(Collectors.toList());
    }
}
