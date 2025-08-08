package com.Cubix.Jobluu.controller;

import com.Cubix.Jobluu.dto.ProfileDto;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Validated
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long id) throws JobluuException {
        try {
            ProfileDto profile = profileService.getProfile(id);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (JobluuException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto profileDto) throws JobluuException {
        try {
            ProfileDto updatedProfile = profileService.updateProfile(profileDto);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (JobluuException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> updateProfileById(
            @PathVariable Long id,
            @RequestBody ProfileDto profileDto) {
        try {
            profileDto.setId(id);
            ProfileDto updatedProfile = profileService.updateProfile(profileDto);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (JobluuException e) {
            System.err.println("Profile update error for ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unexpected error updating profile ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto profileDto) {
        try {
            ProfileDto createdProfile = profileService.createProfile(profileDto);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
