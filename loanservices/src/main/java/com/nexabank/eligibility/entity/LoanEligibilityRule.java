package com.nexabank.eligibility.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "loan_eligibility_rules", schema = "loanschema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanEligibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_eid")
    private UUID loanEid;

    @Column(name = "loan_type_id", nullable = false)
    private UUID loanTypeId;

    @Column(name = "max_loan_amount")
    private BigDecimal maxLoanAmount;

    @Column(name = "apr")
    private BigDecimal apr;

    @Column(name = "min_salary", nullable = false)
    private BigDecimal minSalary;

    @Column(name = "min_credit_score", nullable = false)
    private Integer minCreditScore;

    @Column(name = "max_credit_score")
    private Integer maxCreditScore;

    @Column(name = "loan_point_id")
    private UUID loanPointId;

    public boolean matchesCreditScore(int score) {
        return score >= this.minCreditScore &&
                (this.maxCreditScore == null || score <= this.maxCreditScore);
    }

    public boolean requiresLoanPoints() {
        return this.apr == null && this.loanPointId != null;
    }

    public boolean isDirectApproval() {
        return this.apr != null;
    }
}
