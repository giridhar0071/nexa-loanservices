package com.nexabank.loanservices.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "loan_points", schema = "loanschema")
public class LoanPoints {

    @Id
    @GeneratedValue
    @Column(name = "loan_point_id", updatable = false, nullable = false)
    private UUID loanPointId;

    @Column(name = "loan_type_id", nullable = false)
    private UUID loanTypeId;

    @Column(name = "max_loan_amount")
    private Double maxLoanAmount;

    @Column(name = "percentage_on_loan_approval", nullable = false)
    private Double percentageOnLoanApproval;

    @Column(name = "min_credit_score", nullable = false)
    private Integer minCreditScore;

    @Column(name = "max_credit_score", nullable = false)
    private Integer maxCreditScore;

    // Getters & Setters


    public UUID getLoanPointId() {
        return loanPointId;
    }

    public void setLoanPointId(UUID loanPointId) {
        this.loanPointId = loanPointId;
    }

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
