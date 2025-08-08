package com.Cubix.Jobluu.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;

}
