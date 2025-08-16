package com.vkt.pms.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkt.pms.model.PatientNotificationEvents;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "patient",groupId = "notification-service")
    public void consumePatientDetail(byte[] patientDetail) throws IOException{
        try {
            PatientNotificationEvents patientEvent = objectMapper.readValue(patientDetail,PatientNotificationEvents.class);
            LOGGER.info("Received Patient creation notification {}", patientEvent);
        } catch (IOException e) {
            LOGGER.error("Error while parsing Patient notification event {}", e.getLocalizedMessage());
            throw e;
        }
    }
}
