package com.vkt.pms.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vkt.pms.validation.annotation.DatePattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegistrationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be at least 8 character long")
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    private String address;

    @NotBlank(message = "Date of birth is required")
    @DatePattern(pattern = "yyyy-MM-dd", message = "Date must be in yyyy-MM-dd format")
    private String dateOfBirth;

    @NotBlank(message = "User role is required")
    private String role;
}
