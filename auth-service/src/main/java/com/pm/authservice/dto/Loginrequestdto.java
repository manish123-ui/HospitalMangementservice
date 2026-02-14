package com.pm.authservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Loginrequestdto {
    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min=8,message = "passowrd must be of 8 size")
    private String password;


}
