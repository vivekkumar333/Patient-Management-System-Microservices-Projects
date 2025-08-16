package com.vkt.pms.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkt.pms.events.PatientNotificationEvents;
import com.vkt.pms.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendPatientCreatedEvent(Patient patient){
        PatientNotificationEvents patientEvent = PatientNotificationEvents.builder()
                .patientId(patient.getUid())
                .name(patient.getName())
                .email(patient.getEmail())
                .eventType("PATIENT_CREATED")
                .build();
        try{
            byte[] message = objectMapper.writeValueAsBytes(patientEvent);
            kafkaTemplate.send("patient", message);
        }catch (Exception ex){
            throw new RuntimeException("Error in sending patient created event:" + ex.getMessage(), ex);
        }
    }

}
