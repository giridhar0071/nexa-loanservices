package com.nexabank.loanservices.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class LoanPointsRequest {

    @NotNull(message = "Loan type ID is required")
    private UUID loanTypeId;

    @PositiveOrZero(message = "Max loan amount must be >= 0")
    private Double maxLoanAmount;

    @NotNull(message = "Percentage is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Percentage must be > 0")
    private Double percentageOnLoanApproval;

    @NotNull(message = "Min credit score is required")
    @Min(500) @Max(850)
    private Integer minCreditScore;

    @NotNull(message = "Max credit score is required")
    @Min(500) @Max(850)
    private Integer maxCreditScore;


    // Getters & Setters


    public UUID getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(UUID loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Double getPercentageOnLoanApproval() {
        return percentageOnLoanApproval;
    }

    public void setPercentageOnLoanApproval(Double percentageOnLoanApproval) {
        this.percentageOnLoanApproval = percentageOnLoanApproval;
    }

    public Integer getMinCreditScore() {
        return minCreditScore;
    }

    public void setMinCreditScore(Integer minCreditScore) {
        this.minCreditScore = minCreditScore;
    }

    public Integer getMaxCreditScore() {
        return maxCreditScore;
    }

    public void setMaxCreditScore(Integer maxCreditScore) {
        this.maxCreditScore = maxCreditScore;
    }
}
