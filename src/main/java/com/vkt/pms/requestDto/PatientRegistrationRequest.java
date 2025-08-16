package com.vkt.pms.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRegistrationRequest {
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
}
