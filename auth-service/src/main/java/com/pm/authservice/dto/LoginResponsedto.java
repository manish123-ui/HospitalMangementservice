package com.pm.authservice.dto;

import lombok.Getter;



@Getter
public class LoginResponsedto {
    private final String token;
    public LoginResponsedto(String token) {
        this.token = token;
    }


}
