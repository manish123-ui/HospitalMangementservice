package com.example.demo.controller;

import com.example.demo.dto.Patientdto;
import com.example.demo.services.patientmanagementservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name="Patien",description = "API FOR MANAGING PATIENT")
public class patientcontroller {
    public final patientmanagementservice patientservice;
    @GetMapping("/{id}")
    @Operation(summary = "Get  patients with id")
    public ResponseEntity<Patientdto> Getpatient(@PathVariable UUID id){
        return patientservice.getpatientbyid(id);
    }
    @PostMapping("create/")
    @Operation(summary = "Create the patients")
    public ResponseEntity<Patientdto> create(@RequestBody Patientdto patientdto){
        return patientservice.Create(patientdto);
    }
    @GetMapping
    @Operation(summary = "Get all the patients")
    public ResponseEntity<List<Patientdto>> getallpatient(){
        return ResponseEntity.ok(patientservice.Getallpatient());
    }
    @PutMapping("/{id}")
    @Operation(summary = "update the patients")
    public ResponseEntity<Patientdto> update(@RequestBody Patientdto patientdto,@PathVariable UUID id){
        return patientservice.Update(id,patientdto);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the patients")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id){
        return patientservice.Delete(id);
    }

}
