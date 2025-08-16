package com.vkt.pms.repository;

import com.vkt.pms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByUid(Long patientId);
    Optional<Patient> findByUid(Long patientId);
    boolean existsByEmail(String email);

}
