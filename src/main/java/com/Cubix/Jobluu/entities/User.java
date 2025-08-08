package com.Cubix.Jobluu.entities;

import com.Cubix.Jobluu.dto.AccountType;
import com.Cubix.Jobluu.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private Long id;
    private String name;
    @Indexed(unique = true)//email should be unique for every user
    private String email;
    private String password;
    private byte[] profileLogo;
    private String googleId;
    private String profilePicture;


    private AccountType accountType;
    private String profileId;

    public UserDto toDto(){
        return new UserDto(this.id,this.name,this.email,this.password,this.accountType,this.profileId,this.googleId,this.profilePicture);

    }

}
