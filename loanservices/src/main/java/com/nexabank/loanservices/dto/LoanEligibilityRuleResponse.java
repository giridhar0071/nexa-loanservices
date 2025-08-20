package com.nexabank.loanservices.dto;

import java.util.UUID;

public class LoanEligibilityRuleResponse {

    private UUID loanEid;
    private UUID loanTypeId;
    private Double maxLoanAmount;
    private Double apr;
    private Double minSalary;
    private Integer minCreditScore;
    private Integer maxCreditScore;
    private UUID loanPointId;

    public LoanEligibilityRuleResponse(UUID loanEid, UUID loanTypeId, Double maxLoanAmount,
                                       Double apr, Double minSalary, Integer minCreditScore,
                                       Integer maxCreditScore, UUID loanPointId) {
        this.loanEid = loanEid;
        this.loanTypeId = loanTypeId;
        this.maxLoanAmount = maxLoanAmount;
        this.apr = apr;
        this.minSalary = minSalary;
        this.minCreditScore = minCreditScore;
        this.maxCreditScore = maxCreditScore;
        this.loanPointId = loanPointId;
    }

    // Getters


    public UUID getLoanEid() {
        return loanEid;
    }

    public UUID getLoanTypeId() {
        return loanTypeId;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public Double getApr() {
        return apr;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public Integer getMinCreditScore() {
        return minCreditScore;
    }

    public Integer getMaxCreditScore() {
        return maxCreditScore;
    }

    public UUID getLoanPointId() {
        return loanPointId;
    }
}
