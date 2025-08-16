package com.vkt.pms.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vkt.pms.enums.Role;
import com.vkt.pms.enums.Status;
import com.vkt.pms.validation.annotation.DatePattern;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UserResponse(
        Long uid,
        String email,
        String role,
        String status,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdTimestamp,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime updatedTimestamp
){}
