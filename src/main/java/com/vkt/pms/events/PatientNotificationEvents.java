package com.vkt.pms.events;

import lombok.*;

@Builder
@Data
public class PatientNotificationEvents {
    private Long patientId;
    private String name;
    private String email;
    private String eventType;

}
