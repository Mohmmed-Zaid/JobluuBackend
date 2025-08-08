package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.dto.JobDTO;
import com.Cubix.Jobluu.exception.JobluuException;

import java.util.List;

public interface JobService {
   JobDTO postJob(JobDTO jobDTO) throws JobluuException;
   List<JobDTO> getAllJobs();
   JobDTO getJobById(Long id) throws JobluuException;
   JobDTO updateJob(Long id, JobDTO updatedJob) throws JobluuException;
   void deleteJobById(Long id) throws JobluuException;
   List<JobDTO> getJobsByStatus(String status);
   List<JobDTO> searchJobs(String query);
}