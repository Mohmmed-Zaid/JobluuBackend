package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.dto.ProfileDto;
import com.Cubix.Jobluu.exception.JobluuException;

public interface ProfileService {

    ProfileDto createProfile(String email) throws JobluuException;  // Fixed: Added this method
    ProfileDto createProfile(ProfileDto profileDto) throws JobluuException;
    ProfileDto getProfile(Long id) throws JobluuException;
    ProfileDto updateProfile(ProfileDto profileDto) throws JobluuException;
}