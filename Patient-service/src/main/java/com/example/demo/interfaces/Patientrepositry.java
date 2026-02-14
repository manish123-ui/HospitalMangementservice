package com.example.demo.interfaces;
import com.example.demo.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface Patientrepositry extends JpaRepository<Patient,UUID> {

    Optional<Patient> findByEmail(String email);
}
