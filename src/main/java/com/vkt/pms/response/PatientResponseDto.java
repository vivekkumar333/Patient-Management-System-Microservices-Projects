package com.vkt.pms.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = {"patientId","email"})
@ToString(of = {"patientId","email"})
public class PatientResponseDto {
    private Long patientId;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
    private String registeredDate;
}
