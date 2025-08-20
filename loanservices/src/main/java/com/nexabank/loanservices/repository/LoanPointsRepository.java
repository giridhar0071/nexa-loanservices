package com.nexabank.loanservices.repository;

import com.nexabank.loanservices.entity.LoanPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanPointsRepository extends JpaRepository<LoanPoints, UUID> {
    List<LoanPoints> findByLoanTypeId(UUID loanTypeId);
}
