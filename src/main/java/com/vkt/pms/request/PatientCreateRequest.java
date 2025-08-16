package com.vkt.pms.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientCreateRequest {
    @NotNull(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Name should have at least 2 and max 100 characters")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Address is required")
    @Size(min = 3, max = 250, message = "Address should have at least 10 and max 250 characters")
    private String address;

    @NotNull(message = "Patient date of birth is required")
    private String dateOfBirth;
}
