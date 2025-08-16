package com.vkt.pms.request;

import java.math.BigDecimal;

public record BillingTransactionRequest(
        String transactionType,
        BigDecimal amount,
        String remarks
) {}
