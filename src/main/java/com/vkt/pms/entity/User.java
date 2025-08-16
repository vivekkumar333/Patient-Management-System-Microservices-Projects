package com.vkt.pms.entity;

import com.vkt.pms.enums.Role;
import com.vkt.pms.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user", schema = "auth_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt hash

    @Enumerated(EnumType.STRING)
    private Role role; // PATIENT, STAFF, MANAGER

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE, INACTIVE

    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;
}
