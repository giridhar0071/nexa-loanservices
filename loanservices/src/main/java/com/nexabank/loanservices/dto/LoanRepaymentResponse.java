package com.nexabank.loanservices.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanRepaymentResponse {
    private UUID repaymentId;
    private UUID loanId;
    private Integer months;
    private BigDecimal apr;
    private BigDecimal monthlyPayments;
    private BigDecimal totalPrincipalAmount;
    private BigDecimal totalPayableAmount;

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
}
