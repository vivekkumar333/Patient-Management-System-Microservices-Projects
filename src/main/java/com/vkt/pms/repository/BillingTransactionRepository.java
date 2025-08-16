package com.vkt.pms.repository;

import com.vkt.pms.entity.BillingTransaction;
import com.vkt.pms.service.BillingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingTransactionRepository extends JpaRepository<BillingTransaction, Long> {
}
