package com.vkt.pms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "billing_transaction", schema = "billing_schema")
public class BillingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionSequence")
    @SequenceGenerator(name = "TransactionSequence", sequenceName = "TRANSACTION_SEQUENCE" , allocationSize = 1)
    @Column(name = "uid")
    private Long uid;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = BillingAccount.class)
    @JoinColumn( name = "billing_id", referencedColumnName = "uid", foreignKey = @ForeignKey(name = "fk_BILLING_ID"), nullable = false)
    private BillingAccount billingAccount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_timestamp", nullable = false)
    private LocalDateTime createdTimestamp;
}
