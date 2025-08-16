package com.vkt.pms.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillingRequest {
    @NotNull(message = "Patient id is required")
    private Long patientId;

    @Valid
    @NotEmpty
    List<BillingTransactionRequest> billingTransactionRequests;
}
