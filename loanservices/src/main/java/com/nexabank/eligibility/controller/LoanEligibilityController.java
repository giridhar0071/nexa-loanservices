package com.nexabank.eligibility.controller;

import com.nexabank.eligibility.dto.LoanEligibilityCheckRequest;
import com.nexabank.eligibility.dto.LoanEligibilityCheckResponse;
import com.nexabank.eligibility.service.LoanEligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
@RequiredArgsConstructor
public class LoanEligibilityController {

    private final LoanEligibilityService eligibilityService;

    // Check loan eligibility
    @PostMapping("/check")
    public ResponseEntity<LoanEligibilityCheckResponse> checkEligibility(
            @RequestBody LoanEligibilityCheckRequest request,
            @RequestParam String loanTypeName,    // will be fetched from Loan Type Service in real integration
            @RequestParam Integer creditScore,    // will be fetched from User Service
            @RequestParam java.math.BigDecimal annualSalary // will be fetched from User Service
    ) {
        LoanEligibilityCheckResponse response =
                eligibilityService.checkEligibility(request, loanTypeName, creditScore, annualSalary);
        return ResponseEntity.ok(response);
    }
}
