package com.nexabank.loanservices.service;

import com.nexabank.loanservices.dto.LoanRepaymentRequest;
import com.nexabank.loanservices.dto.LoanRepaymentResponse;
import com.nexabank.loanservices.entity.ApprovedLoanRepaymentOption;
import com.nexabank.loanservices.repository.LoanRepaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanRepaymentService {

    private final LoanRepaymentRepository repository;

    public LoanRepaymentService(LoanRepaymentRepository repository) {
        this.repository = repository;
    }

    /**
     * Create repayment plan
     */
    public LoanRepaymentResponse createRepaymentPlan(LoanRepaymentRequest request) {
        // Calculate monthly payments using formula
        BigDecimal monthlyRate = request.getApr()
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal numerator = monthlyRate.multiply(request.getTotalPrincipalAmount());
        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(
                        BigDecimal.valueOf(Math.pow(1 + monthlyRate.doubleValue(), request.getMonths())),
                        10, RoundingMode.HALF_UP
                )
        );

        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        BigDecimal totalPayable = monthlyPayment.multiply(BigDecimal.valueOf(request.getMonths()));

        ApprovedLoanRepaymentOption repayment = new ApprovedLoanRepaymentOption();
        repayment.setLoanId(request.getLoanId());
        repayment.setMonths(request.getMonths());
        repayment.setApr(request.getApr());
        repayment.setMonthlyPayments(monthlyPayment);
        repayment.setTotalPrincipalAmount(request.getTotalPrincipalAmount());
        repayment.setTotalPayableAmount(totalPayable);

        ApprovedLoanRepaymentOption saved = repository.save(repayment);

        return mapToResponse(saved);
    }

    /**
     * Get repayment by LoanId
     */
    public LoanRepaymentResponse getByLoanId(UUID loanId) {
        ApprovedLoanRepaymentOption entity = repository.findByLoanId(loanId)
                .orElseThrow(() -> new RuntimeException("Repayment plan not found for loanId: " + loanId));
        return mapToResponse(entity);
    }

    /**
     * List all repayments
     */
    public List<LoanRepaymentResponse> getAllRepayments() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update repayment plan
     */
    public LoanRepaymentResponse updateRepayment(UUID repaymentId, LoanRepaymentRequest request) {
        ApprovedLoanRepaymentOption entity = repository.findById(repaymentId)
                .orElseThrow(() -> new RuntimeException("Repayment plan not found with id: " + repaymentId));

        // Recalculate monthly payments
        BigDecimal monthlyRate = request.getApr()
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal numerator = monthlyRate.multiply(request.getTotalPrincipalAmount());
        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(
                        BigDecimal.valueOf(Math.pow(1 + monthlyRate.doubleValue(), request.getMonths())),
                        10, RoundingMode.HALF_UP
                )
        );

        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        BigDecimal totalPayable = monthlyPayment.multiply(BigDecimal.valueOf(request.getMonths()));

        // Update entity
        entity.setMonths(request.getMonths());
        entity.setApr(request.getApr());
        entity.setMonthlyPayments(monthlyPayment);
        entity.setTotalPrincipalAmount(request.getTotalPrincipalAmount());
        entity.setTotalPayableAmount(totalPayable);

        ApprovedLoanRepaymentOption updated = repository.save(entity);

        return mapToResponse(updated);
    }

    /**
     * Delete repayment plan
     */
    public void deleteRepayment(UUID repaymentId) {
        if (!repository.existsById(repaymentId)) {
            throw new RuntimeException("Repayment plan not found with id: " + repaymentId);
        }
        repository.deleteById(repaymentId);
    }

    /**
     * Utility mapper
     */
    private LoanRepaymentResponse mapToResponse(ApprovedLoanRepaymentOption entity) {
        LoanRepaymentResponse resp = new LoanRepaymentResponse();
        resp.setRepaymentId(entity.getRepaymentId());
        resp.setLoanId(entity.getLoanId());
        resp.setMonths(entity.getMonths());
        resp.setApr(entity.getApr());
        resp.setMonthlyPayments(entity.getMonthlyPayments());
        resp.setTotalPrincipalAmount(entity.getTotalPrincipalAmount());
        resp.setTotalPayableAmount(entity.getTotalPayableAmount());
        return resp;
    }
}