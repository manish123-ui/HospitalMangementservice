package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class kafka_consumer {
    private static final Logger log = LoggerFactory.getLogger(kafka_consumer.class);

    @KafkaListener(topics = "patient",groupId = "analytics-service")
    public void consumervent(byte[] event){
        try {
            PatientEvent patientEvent= PatientEvent.parseFrom(event);
            //do all the business logic
            log.info("Received Patient Event: [PatientId={},PatientName={},PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error in desrialize the evennt",e.getMessage());
            //if we throw exception that can create chaos
        }


    }

}
