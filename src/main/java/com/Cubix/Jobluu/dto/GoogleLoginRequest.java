package com.Cubix.Jobluu.dto;

import com.Cubix.Jobluu.dto.AccountType;
import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String credential;
    private AccountType accountType;
}