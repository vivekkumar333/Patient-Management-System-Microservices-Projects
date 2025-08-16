package com.vkt.pms.controller;

import com.vkt.pms.request.PatientCreateRequest;
import com.vkt.pms.response.PatientResponseDto;
import com.vkt.pms.request.PatientUpdateRequest;
import com.vkt.pms.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
@Tag(name = "Patient", description = "Operations related to Patient")
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientCreateRequest patientCreateRequest) {
        return ResponseEntity.ok().body(patientService.savePatientDetails(patientCreateRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<PatientResponseDto> updatePatientDetails(@PathVariable("id") Long patientId, @Valid @RequestBody PatientUpdateRequest patientUpdateRequest) {
        return ResponseEntity.ok().body(patientService.updatePatientDetails(patientId, patientUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "Fetch all patient details")
    public ResponseEntity<List<PatientResponseDto>> fetchAllPatientDetails(){
        return ResponseEntity.ok().body(patientService.fetchAllPatientDetails());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient")
    public ResponseEntity<PatientResponseDto> fetchPatientDetails(@PathVariable("id") Long patientId){
        return ResponseEntity.ok().body(patientService.fetchPatientDetails(patientId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Map<String,String>> deletePatientDetails(@PathVariable("id") Long patientId){
        patientService.deletePatientDetails(patientId);
        return ResponseEntity.ok(Map.of("message","Patient deleted successfully"));
    }
}