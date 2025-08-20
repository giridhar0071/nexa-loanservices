package com.nexabank.loanservices.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class LoanRepaymentRequest {

    @NotNull(message = "LoanId is required")
    private UUID loanId;

    @NotNull
    @Min(value = 1, message = "Months must be > 0")
    private Integer months;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "APR must be >= 0")
    @DecimalMax(value = "36.0", inclusive = true, message = "APR must be <= 36")
    private BigDecimal apr;

    @NotNull
    @DecimalMin(value = "0.0", message = "Principal must be >= 0")
    private BigDecimal totalPrincipalAmount;

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getTotalPrincipalAmount() {
        return totalPrincipalAmount;
    }

    public void setTotalPrincipalAmount(BigDecimal totalPrincipalAmount) {
        this.totalPrincipalAmount = totalPrincipalAmount;
    }
}
