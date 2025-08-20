package com.nexabank.loanservices.repository;

import com.nexabank.loanservices.entity.LoanEligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanEligibilityRuleRepository extends JpaRepository<LoanEligibilityRule, UUID> {
    List<LoanEligibilityRule> findByLoanTypeId(UUID loanTypeId);
}
