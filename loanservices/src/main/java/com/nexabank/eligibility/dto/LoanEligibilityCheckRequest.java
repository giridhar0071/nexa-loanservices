package com.nexabank.eligibility.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanEligibilityCheckRequest {
    private UUID userId;
    private UUID loanTypeId;
    private BigDecimal requestedAmount;
}
