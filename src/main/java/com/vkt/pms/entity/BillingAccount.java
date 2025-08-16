package com.vkt.pms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"patientUId"})
@ToString(of = {"patientUId","accountBalance","status","createdTimeStamp","updateTimeStamp"})
@Entity
@Table(name = "billing_account", schema = "billing_schema")
public class BillingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="BillingSequence")
    @SequenceGenerator(name = "BillingSequence", sequenceName = "BILLING_SEQUENCE", allocationSize = 1)
    @Column(name = "uid")
    private Long  uid;

    @Column(name = "patient_uid", nullable = false,unique = true)
    private Long patientUId;

    @Column(name = "account_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal accountBalance;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_timestamp", nullable = false)
    private LocalDateTime createdTimeStamp;

    @Column(name = "updated_timestamp")
    private LocalDateTime updateTimeStamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "billingAccount")
    private List<BillingTransaction> billingTransaction;
}
