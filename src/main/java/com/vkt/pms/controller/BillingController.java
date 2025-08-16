package com.vkt.pms.controller;

import com.vkt.pms.request.BillingRequest;
import com.vkt.pms.response.BillingResponse;
import com.vkt.pms.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/billing")
@Tag(name = "Billing", description = "Operations related to Billing")
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/account")
    @Operation(summary = "Create a billing account")
    public ResponseEntity<BillingResponse> createBillingAccount(@Valid @RequestBody BillingRequest billingRequest) {
        return ResponseEntity.ok().body(billingService.createBillingAccount(billingRequest));
    }

    @PostMapping("/add")
    @Operation(summary = "Add new bill's")
    public ResponseEntity<BillingResponse> addBills(@Valid @RequestBody BillingRequest billingRequest) {
        return ResponseEntity.ok().body(billingService.addNewBills(billingRequest));
    }

    @PostMapping("/receive-payment")
    @Operation(summary = "Receive payment")
    public ResponseEntity<BillingResponse> receivedPayment(@Valid @RequestBody BillingRequest billingRequest) {
        return ResponseEntity.ok().body(billingService.receivePayment(billingRequest));
    }
}