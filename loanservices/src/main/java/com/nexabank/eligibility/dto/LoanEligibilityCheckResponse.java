package com.nexabank.eligibility.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanEligibilityCheckResponse {

    private boolean eligible;             //  Overall eligibility
    private String loanType;              //  Loan type name
    private BigDecimal sanctionedAmount;  // Approved/sanctioned amount

    private BigDecimal apr;               //  APR if direct approval

    private boolean requiresLoanPoints;   //  True if APR is null and loanPointId exists
    private UUID loanPointId;             //  Reference to loan points (if applicable)

    private List<String> reasons;         //  Reasons for rejection or notes
}

