package com.nexabank.loanservices.dto;

import java.util.UUID;

public class LoanPointsResponse {

    private UUID loanPointId;
    private UUID loanTypeId;
    private Double maxLoanAmount;
    private Double percentageOnLoanApproval;
    private Integer minCreditScore;
    private Integer maxCreditScore;

    public LoanPointsResponse(UUID loanPointId, UUID loanTypeId, Double maxLoanAmount, Double percentageOnLoanApproval, Integer minCreditScore, Integer maxCreditScore) {
        this.loanPointId = loanPointId;
        this.loanTypeId = loanTypeId;
        this.maxLoanAmount = maxLoanAmount;
        this.percentageOnLoanApproval = percentageOnLoanApproval;
        this.minCreditScore = minCreditScore;
        this.maxCreditScore = maxCreditScore;
    }
    // Getters


    public UUID getLoanPointId() {
        return loanPointId;
    }

    public UUID getLoanTypeId() {
        return loanTypeId;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public Double getPercentageOnLoanApproval() {
        return percentageOnLoanApproval;
    }

    public Integer getMinCreditScore() {
        return minCreditScore;
    }

    public Integer getMaxCreditScore() {
        return maxCreditScore;
    }
}
