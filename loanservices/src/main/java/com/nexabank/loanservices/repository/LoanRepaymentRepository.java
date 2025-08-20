package com.nexabank.loanservices.repository;

import com.nexabank.loanservices.entity.ApprovedLoanRepaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRepaymentRepository extends JpaRepository<ApprovedLoanRepaymentOption, UUID> {
    Optional<ApprovedLoanRepaymentOption> findByLoanId(UUID loanId);
}
