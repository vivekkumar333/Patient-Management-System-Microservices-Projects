package com.vkt.pms.mapper;

import com.vkt.pms.request.PatientCreateRequest;
import com.vkt.pms.response.PatientResponseDto;
import com.vkt.pms.entity.Patient;

import java.time.LocalDate;

public class PatientObjectMapper {
    public static PatientResponseDto patientToPatientResponseDto(Patient patient){
        return PatientResponseDto.builder()
                .patientId(patient.getUid())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .registeredDate(patient.getRegisteredDate().toString())
                .build();
    }

    public static Patient patientCreateRequestDtoToPatient(PatientCreateRequest patientCreateRequest){
        return Patient.builder()
                .name(patientCreateRequest.getName())
                .email(patientCreateRequest.getEmail())
                .address(patientCreateRequest.getAddress())
                .dateOfBirth(LocalDate.parse(patientCreateRequest.getDateOfBirth()))
                .build();
    }

}
