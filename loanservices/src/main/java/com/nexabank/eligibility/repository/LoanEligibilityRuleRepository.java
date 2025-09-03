package com.nexabank.eligibility.repository;

import com.nexabank.eligibility.entity.LoanEligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanEligibilityRuleRepository extends JpaRepository<LoanEligibilityRule, UUID> {
    List<LoanEligibilityRule> findByLoanTypeId(UUID loanTypeId);
    LoanEligibilityRule findFirstByLoanTypeIdAndMinCreditScoreLessThanEqualAndMaxCreditScoreGreaterThanEqual(
            UUID loanTypeId,
            Integer creditScore1,
            Integer creditScore2
    );
}
