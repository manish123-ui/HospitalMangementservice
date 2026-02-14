package com.pm.authservice.service;


import com.pm.authservice.dto.Loginrequestdto;
import com.pm.authservice.repositry.userrepositry;
import com.pm.authservice.util.jwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class authservice {
    private final userrepositry userepositry;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtil jwtutil;

    public Optional<String> authenticate(Loginrequestdto loged) {
        Optional<String> token = userepositry.findByEmail(loged.getEmail())
                .filter(u -> passwordEncoder.matches(loged.getPassword(), u.getPassword()))
                .map(u -> jwtutil.generateToken(u.getEmail(), u.getRole()));
        return token;
    }

    public boolean validateToken(String token) {
        try {
            jwtutil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}