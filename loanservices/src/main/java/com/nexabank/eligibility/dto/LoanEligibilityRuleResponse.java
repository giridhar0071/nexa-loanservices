package com.nexabank.eligibility.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoanEligibilityRuleResponse {
    private UUID loanEid;
    private UUID loanTypeId;
    private BigDecimal maxLoanAmount;
    private BigDecimal apr;
    private BigDecimal minSalary;
    private Integer minCreditScore;
    private Integer maxCreditScore;
    private UUID loanPointId;
}
