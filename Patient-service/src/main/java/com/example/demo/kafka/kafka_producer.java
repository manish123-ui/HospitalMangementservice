package com.example.demo.kafka;


import com.example.demo.entities.Patient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;


@Service
public class kafka_producer {
    private static final Logger log = LoggerFactory.getLogger(
            KafkaProducer.class);
    private final KafkaTemplate<String,byte[]> kafkaTemplate;//input o/p for how it flow
    public kafka_producer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
   public void sendEvenet(Patient patient){
       PatientEvent event = PatientEvent.newBuilder()
               .setPatientId(patient.getId().toString())
               .setName(patient.getName())
               .setEmail(patient.getEmail())
               .setEventType("PATIENT_CREATED")
               .build();
       try {
           kafkaTemplate.send("patient", event.toByteArray());
       } catch (Exception e) {
           log.error("Error sending PatientCreated event: {}", event);
       }
   }

   }


