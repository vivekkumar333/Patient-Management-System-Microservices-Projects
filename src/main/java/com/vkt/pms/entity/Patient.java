package com.vkt.pms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"email"})
@ToString(of = {"uid","email","name"})
@Entity
@Table(name = "patient",schema = "patient_schema") // schema = "patient_schema"
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PatientSequence" )
    @SequenceGenerator(name = "PatientSequence",sequenceName = "PATIENT_SEQUENCE" ,allocationSize = 1)
    @Column(name = "uid", nullable = false)
    private Long uid;

    private String name;

    @Column(unique = true)
    private String email;

    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;
}
