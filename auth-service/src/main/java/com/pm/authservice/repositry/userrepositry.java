package com.pm.authservice.repositry;

import com.pm.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface userrepositry extends JpaRepository<User, UUID> {
Optional<User> findByEmail(String email);
}
