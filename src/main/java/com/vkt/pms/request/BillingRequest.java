package com.vkt.pms.request;

import java.util.List;

public record BillingRequest(
        Long patientId,
        List<BillingTransactionRequest> billingTransactionRequests
) {}
