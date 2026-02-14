package com.example.demo.services;


import billing.BillingResponse;
import com.example.demo.Grpc.BillingServiceGrpcClient;
import com.example.demo.dto.Patientdto;
import com.example.demo.entities.Patient;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.interfaces.Patientrepositry;
import com.example.demo.kafka.kafka_producer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@ToString
public class patientmanagementservice {
    private final Patientrepositry patientrepositry;
    private final ModelMapper modelMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final kafka_producer kafkaProducer;

    public ResponseEntity<Patientdto> getpatientbyid(UUID id){
        log.info("fetching the patient by id "+id);
        Patient patient =patientrepositry.findById(id)
                .orElseThrow(() -> {
                    log.error("patient not found by this id");
                    return new EntityNotFoundException("Employee not found with id: " + id);
                });

        return ResponseEntity.ok(modelMapper.map(patient, Patientdto.class));
    }
    public List<Patientdto> Getallpatient(){
        List<Patient> patients=patientrepositry.findAll();
        return patients.stream()
                .map(patient -> modelMapper.map(patient, Patientdto.class))
                .toList();
    }
    public ResponseEntity<Patientdto> Create(Patientdto patientdto){
        Optional<Patient> patient=patientrepositry.findByEmail(patientdto.getEmail());
        if(!patient.isEmpty()){
            log.error("Employee already exists with email: {}", patientdto.getEmail());
            throw new RuntimeException("Employee already exists with email: " + patientdto.getEmail());
        }
        Patient patient1=modelMapper.map(patientdto,Patient.class);
        Patient savedpatient=patientrepositry.save(patient1);
        billingServiceGrpcClient.createBillingAccount(savedpatient.getId().toString(),
                savedpatient.getName(), savedpatient.getEmail());
        kafkaProducer.sendEvenet(savedpatient);
        return ResponseEntity.ok(modelMapper.map(savedpatient,Patientdto.class));
    }
    public ResponseEntity<Patientdto> Update(UUID id,Patientdto patientdto){
        Patient patient=patientrepositry.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee already exists with email: " + id));
        patient.setId(id);
        Patient savedpatient=patientrepositry.save(patient);
        return ResponseEntity.ok(modelMapper.map(savedpatient,Patientdto.class));
    }


    public ResponseEntity<Boolean> Delete(UUID id) {
        Patient patient=patientrepositry.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee already exists with email: " + id));
        patientrepositry.delete(patient);
        return ResponseEntity.ok(true);
    }
}
