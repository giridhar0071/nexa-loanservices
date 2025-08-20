package com.nexabank.loanservices.entity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "loan_eligibility_rules", schema = "loanschema")
public class LoanEligibilityRule {

    @Id
    @GeneratedValue
    @Column(name = "loan_eid", updatable = false, nullable = false)
    private UUID loanEid;

    @Column(name = "loan_type_id", nullable = false)
    private UUID loanTypeId;

    @Column(name = "max_loan_amount")
    private Double maxLoanAmount;

    @Column(name = "apr")
    private Double apr;

    @Column(name = "min_salary", nullable = false)
    private Double minSalary;

    @Column(name = "min_credit_score", nullable = false)
    private Integer minCreditScore;

    @Column(name = "max_credit_score")
    private Integer maxCreditScore;

    @Column(name = "loan_point_id")
    private UUID loanPointId;

    public UUID getLoanEid() {
        return loanEid;
    }

    public void setLoanEid(UUID loanEid) {
        this.loanEid = loanEid;
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

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
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

    public UUID getLoanPointId() {
        return loanPointId;
    }

    public void setLoanPointId(UUID loanPointId) {
        this.loanPointId = loanPointId;
    }
}
