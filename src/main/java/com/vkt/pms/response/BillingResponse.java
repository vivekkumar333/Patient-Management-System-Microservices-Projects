package com.vkt.pms.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillingResponse {
    private Long billingTransactionId;
    private BigDecimal billingAmount;
    private BigDecimal accountBalance;
    private String message;
}
