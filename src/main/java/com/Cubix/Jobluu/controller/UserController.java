package com.Cubix.Jobluu.controller;

import com.Cubix.Jobluu.dto.LoginDto;
import com.Cubix.Jobluu.dto.ResponseDto;
import com.Cubix.Jobluu.dto.UserDto;
import com.Cubix.Jobluu.exception.JobluuException;
import com.Cubix.Jobluu.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Validated
@RequestMapping("api/users") //can handle all the request like get,post etc
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) throws Exception {
        userDto = userService.registerUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody @Valid LoginDto loginDto) throws Exception {
        return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);

    }

    @PostMapping("/changePass")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody @Valid LoginDto loginDto) throws Exception {
        return new ResponseEntity<>(userService.changePassword(loginDto), HttpStatus.OK);

    }

    @PostMapping("sendOTP/{email}")
    public ResponseEntity<ResponseDto> sendOTP(@PathVariable @Email(message = "{user.email.invalid}") String email) throws Exception{
        userService.sendOTP(email);
        return new ResponseEntity<>(new ResponseDto("OTP sent Successfully.",true),
                HttpStatus.OK);

    }

    @GetMapping("verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDto> verifyOTP(@PathVariable  @Email(message = "{user.email.invalid}") String email, @PathVariable @Pattern(regexp = "^[0-9]{6}$",message = "{otp.invalid}") String otp) throws JobluuException{
        userService.verifyOTP(email,otp);
        return new ResponseEntity<>(new ResponseDto("OTP has been Verified.",true)
                , HttpStatus.OK);

    }

}
