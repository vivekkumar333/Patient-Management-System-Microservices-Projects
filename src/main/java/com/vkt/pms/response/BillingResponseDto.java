package com.vkt.pms.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillingResponseDto {
    private Long billingTransactionId;
    private BigDecimal billingAmount;
    private BigDecimal accountBalance;
    private String message;
}
