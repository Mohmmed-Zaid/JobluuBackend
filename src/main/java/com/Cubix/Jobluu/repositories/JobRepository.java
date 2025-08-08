package com.Cubix.Jobluu.repositories;

import com.Cubix.Jobluu.dto.JobStatus;
import com.Cubix.Jobluu.entities.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, Long> {

    // Fixed method name to match the field name in Job entity
    List<Job> findByJobStatus(JobStatus jobStatus);

    // This method works for MongoDB - no need for @Query
    List<Job> findByJobTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String jobTitle, String company);

    // MongoDB query - fixed syntax
    @Query("{ $or: [ " +
            "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
            "{ 'company': { $regex: ?0, $options: 'i' } }, " +
            "{ 'description': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<Job> searchJobs(String query);

    // Additional useful queries
    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByJobTypeIgnoreCase(String jobType);

    @Query("{ 'skillsRequired': { $in: [?0] } }")
    List<Job> findBySkillsRequired(String skill);
}
