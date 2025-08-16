package com.vkt.pms.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientUpdateRequest {
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Address is required")
    @Size(min = 10, max = 250, message = "Address should have at least 10 and max 250 characters")
    private String address;

    @NotNull(message = "Patient date of birth is required")
    private String dateOfBirth;
}
