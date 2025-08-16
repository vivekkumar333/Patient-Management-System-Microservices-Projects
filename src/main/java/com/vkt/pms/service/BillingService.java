package com.vkt.pms.service;

import com.vkt.pms.request.BillingRequest;
import com.vkt.pms.response.BillingResponse;
import com.vkt.pms.request.BillingTransactionRequest;
import com.vkt.pms.entity.BillingAccount;
import com.vkt.pms.entity.BillingTransaction;
import com.vkt.pms.exception.BillingAccountNotFoundException;
import com.vkt.pms.repository.BillingAccountRepository;
import com.vkt.pms.repository.BillingTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingAccountRepository billingAccountRepository;
    private final BillingTransactionRepository billingTransactionRepository;

    /**
     * Method: Create a billing account for new registered patient.
     * */
    @Transactional
    public BillingResponse createBillingAccount(BillingRequest request) {
        // Check if BillingAccount already exists for patientId
        BillingAccount account = billingAccountRepository.findByPatientUId(request.getPatientId())
                .orElseGet(() -> {
                    BillingAccount newAccount = BillingAccount.builder()
                            .patientUId(request.getPatientId())
                            .accountBalance(BigDecimal.ZERO)
                            .status("ACTIVE")
                            .createdTimeStamp(LocalDateTime.now())
                            .build();
                    return billingAccountRepository.save(newAccount);
                });

        // Create BillingTransaction for Registration Fee
        BillingTransactionRequest billingTransactionRequest = request.getBillingTransactionRequests().get(0);
        BillingTransaction transaction = BillingTransaction.builder()
                .billingAccount(account)
                .transactionType(billingTransactionRequest.getTransactionType())
                .amount(billingTransactionRequest.getAmount().negate()) // Deduct fee (e.g., -100)
                .createdTimestamp(LocalDateTime.now())
                .remarks(billingTransactionRequest.getRemarks())
                .build();

        BillingTransaction saveTransaction = billingTransactionRepository.save(transaction);

        // Update BillingAccount balance
        account.setAccountBalance(account.getAccountBalance().add(transaction.getAmount()));
        account.setUpdateTimeStamp(LocalDateTime.now());
        BillingAccount savedBA=  billingAccountRepository.save(account);

        return BillingResponse.builder()
                .billingTransactionId(saveTransaction.getUid())
                .billingAmount(saveTransaction.getAmount())
                .accountBalance(savedBA.getAccountBalance())
                .message("Billing account created & registration fee deducted")
                .build();
    }


    /**
     * Method: Add new all types of bill's
     * */
    @Transactional
    public BillingResponse addNewBills(BillingRequest request) {
        // Check if BillingAccount already exists for patientId or Not
        BillingAccount account = billingAccountRepository.findByPatientUId(request.getPatientId())
                .orElseThrow(() -> new BillingAccountNotFoundException("Billing Account not found for Patient :"+request.getPatientId()));

        // Create Transaction for newly added bill's
        BigDecimal totalAmt= BigDecimal.ZERO;
        List<BillingTransaction> transactionList = new ArrayList<>();
        for(BillingTransactionRequest t : request.getBillingTransactionRequests()){
            totalAmt = totalAmt.add(t.getAmount());
            BillingTransaction transaction = BillingTransaction.builder()
                    .billingAccount(account)
                    .transactionType(t.getTransactionType())
                    .amount(t.getAmount().negate()) // Deduct fee (e.g., -100)
                    .createdTimestamp(LocalDateTime.now())
                    .remarks(t.getRemarks())
                    .build();
            transactionList.add(transaction);
        };
        List<BillingTransaction> billingTransactions = billingTransactionRepository.saveAll(transactionList);

        // Update Account balance
        account.setAccountBalance(account.getAccountBalance().add(totalAmt));
        account.setUpdateTimeStamp(LocalDateTime.now());
        BillingAccount savedBA=  billingAccountRepository.save(account);

        return BillingResponse.builder()
                .billingAmount(totalAmt)
                .accountBalance(savedBA.getAccountBalance())
                .message("New Bill's have been added successfully")
                .build();
    }



    /**
     * Method: Receive payment and update to the patient balance account.
     * */
    @Transactional
    public BillingResponse receivePayment(BillingRequest request) {
        BillingAccount account = billingAccountRepository.findByPatientUId(request.getPatientId())
                .orElseThrow(() -> new BillingAccountNotFoundException("Billing Account not found for Patient :"+request.getPatientId()));

        // Create BillingTransaction for Payment
        BillingTransactionRequest billingTransactionRequest = request.getBillingTransactionRequests().get(0);
        BillingTransaction transaction = BillingTransaction.builder()
                .billingAccount(account)
                .transactionType(billingTransactionRequest.getTransactionType())
                .amount(billingTransactionRequest.getAmount())
                .createdTimestamp(LocalDateTime.now())
                .remarks(billingTransactionRequest.getRemarks())
                .build();
        BillingTransaction savedTransaction = billingTransactionRepository.save(transaction);

        // Update Account Balance
        account.setAccountBalance(account.getAccountBalance().add(billingTransactionRequest.getAmount()));
        account.setUpdateTimeStamp(LocalDateTime.now());
        BillingAccount savedBA = billingAccountRepository.save(account);

        // Send Notification Event to Kafka
//        PaymentReceivedEvent event = PaymentReceivedEvent.builder()
//                .patientId(account.getPatientId())
//                .paidAmount(paymentRequest.getAmount())
//                .currentBalance(account.getAccountBalance())
//                .message("Your payment of ₹" + paymentRequest.getAmount() + " has been received. Balance is now ₹" + account.getAccountBalance())
//                .build();
//
//        kafkaProducer.sendPaymentReceivedEvent(event);

        return BillingResponse.builder()
                .billingTransactionId(savedTransaction.getUid())
                .billingAmount(savedTransaction.getAmount())
                .accountBalance(savedBA.getAccountBalance())
                .message("Payment received & balance updated")
                .build();
    }
}
