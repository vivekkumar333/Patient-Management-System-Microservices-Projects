package com.vkt.pms.repository;

import com.vkt.pms.entity.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillingAccountRepository extends JpaRepository<BillingAccount, Long> {
    Optional<BillingAccount> findByPatientUId(Long uid);
}
