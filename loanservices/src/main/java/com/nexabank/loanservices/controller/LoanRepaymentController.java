package com.nexabank.loanservices.controller;

import com.nexabank.loanservices.dto.LoanRepaymentRequest;
import com.nexabank.loanservices.dto.LoanRepaymentResponse;
import com.nexabank.loanservices.service.LoanRepaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loanrepayments")
public class LoanRepaymentController {

    private final LoanRepaymentService repaymentService;

    public LoanRepaymentController(LoanRepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    /**
     * Create repayment plan
     */
    @PostMapping
    public ResponseEntity<LoanRepaymentResponse> createRepayment(@RequestBody LoanRepaymentRequest request) {
        LoanRepaymentResponse response = repaymentService.createRepaymentPlan(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get repayment plan by LoanId
     */
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanRepaymentResponse> getRepaymentByLoanId(@PathVariable UUID loanId) {
        LoanRepaymentResponse response = repaymentService.getByLoanId(loanId);
        return ResponseEntity.ok(response);
    }

    /**
     * List all repayment plans (Admin/Backoffice)
     */
    @GetMapping
    public ResponseEntity<List<LoanRepaymentResponse>> getAllRepayments() {
        List<LoanRepaymentResponse> responses = repaymentService.getAllRepayments();
        return ResponseEntity.ok(responses);
    }

    /**
     * Update repayment plan (allowed only if loan not finalized)
     */
    @PutMapping("/{repaymentId}")
    public ResponseEntity<LoanRepaymentResponse> updateRepayment(
            @PathVariable UUID repaymentId,
            @RequestBody LoanRepaymentRequest request) {

        LoanRepaymentResponse response = repaymentService.updateRepayment(repaymentId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete repayment plan (allowed only if loan not finalized)
     */
    @DeleteMapping("/{repaymentId}")
    public ResponseEntity<Void> deleteRepayment(@PathVariable UUID repaymentId) {
        repaymentService.deleteRepayment(repaymentId);
        return ResponseEntity.noContent().build();
    }
}
