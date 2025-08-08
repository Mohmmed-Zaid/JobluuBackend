package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.dto.ProfileDto;
import com.Cubix.Jobluu.entities.Profile;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.repositories.ProfileRepository;
import com.Cubix.Jobluu.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileDto createProfile(String email) throws JobluuException {  // Fixed: Return ProfileDto instead of Long
        Profile profile = new Profile();
        profile.setId(Utilities.getNextSequence("profile"));
        profile.setEmail(email);
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());
        profile.setJobTitle("");
        profile.setCompany("");
        profile.setLocation("");
        profile.setAbout("");
        Profile savedProfile = profileRepository.save(profile);  // Fixed: Store saved profile
        return savedProfile.toDTO();  // Fixed: Return ProfileDto
    }

    @Override
    public ProfileDto createProfile(ProfileDto profileDto) throws JobluuException {
        // Check if profile already exists by email
        if (profileDto.getEmail() != null && !profileDto.getEmail().trim().isEmpty()) {
            // Optional: Check if profile with this email already exists
            // Profile existingProfile = profileRepository.findByEmail(profileDto.getEmail());
            // if (existingProfile != null) {
            //     throw new JobluuException("PROFILE_ALREADY_EXISTS");
            // }
        }

        // Set ID if not provided
        if (profileDto.getId() == null) {
            profileDto.setId(Utilities.getNextSequence("profile"));
        }

        // Initialize empty lists if they are null
        if (profileDto.getSkills() == null) {
            profileDto.setSkills(new ArrayList<>());
        }
        if (profileDto.getExperiences() == null) {
            profileDto.setExperiences(new ArrayList<>());
        }
        if (profileDto.getCertifications() == null) {
            profileDto.setCertifications(new ArrayList<>());
        }

        // Set default values for empty strings
        if (profileDto.getJobTitle() == null) profileDto.setJobTitle("");
        if (profileDto.getCompany() == null) profileDto.setCompany("");
        if (profileDto.getLocation() == null) profileDto.setLocation("");
        if (profileDto.getAbout() == null) profileDto.setAbout("");

        Profile profile = profileDto.toEntity();
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile.toDTO();
    }

    @Override
    public ProfileDto getProfile(Long id) throws JobluuException {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new JobluuException("PROFILE_NOT_FOUND"));
        return profile.toDTO();
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto) throws JobluuException {
        profileRepository.findById(profileDto.getId())
                .orElseThrow(() -> new JobluuException("PROFILE_NOT_FOUND"));
        Profile savedProfile = profileRepository.save(profileDto.toEntity());
        return savedProfile.toDTO();  // Fixed: Return the saved profile DTO
    }
}
