package com.nexabank.loanservices.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "approved_loans_repayment_options", schema = "loanschema")
public class ApprovedLoanRepaymentOption {

    @Id
    @GeneratedValue
    @Column(name = "repayment_id")
    private UUID repaymentId;

    @Column(name = "loan_id", nullable = false, unique = true)
    private UUID loanId;

    @Column(name = "months", nullable = false)
    private Integer months;

    @Column(name = "apr", nullable = false, precision = 5, scale = 2)
    private BigDecimal apr;

    @Column(name = "monthly_payments", nullable = false, precision = 12, scale = 2)
    private BigDecimal monthlyPayments;

    @Column(name = "total_principal_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrincipalAmount;

    @Column(name = "total_payable_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPayableAmount;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public UUID getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(UUID repaymentId) {
        this.repaymentId = repaymentId;
    }

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

    public BigDecimal getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setMonthlyPayments(BigDecimal monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }

    public BigDecimal getTotalPrincipalAmount() {
        return totalPrincipalAmount;
    }

    public void setTotalPrincipalAmount(BigDecimal totalPrincipalAmount) {
        this.totalPrincipalAmount = totalPrincipalAmount;
    }

    public BigDecimal getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
