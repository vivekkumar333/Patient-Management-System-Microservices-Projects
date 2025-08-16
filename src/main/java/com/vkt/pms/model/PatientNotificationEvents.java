package com.vkt.pms.model;

import lombok.Data;

@Data
public class PatientNotificationEvents {
    private Long patientId;
    private String name;
    private String email;
    private String eventType;

}