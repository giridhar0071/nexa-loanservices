package com.nexabank.loanservices.controller;

import com.nexabank.loanservices.dto.LoanEligibilityRuleResponse;
import com.nexabank.loanservices.service.LoanEligibilityRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loaneligibilityrules")
public class LoanEligibilityRuleController {

    private final LoanEligibilityRuleService eligibilityService;

    public LoanEligibilityRuleController(LoanEligibilityRuleService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    // Initialize default rules for a loan type
    @PostMapping("/init/{loanTypeId}")
    public List<LoanEligibilityRuleResponse> createDefaultEligibilityRules(
            @PathVariable UUID loanTypeId,
            @RequestParam Double minSalary) {
        return eligibilityService.createDefaultEligibilityRules(loanTypeId, minSalary);
    }

    // Get all eligibility rules
    @GetMapping
    public List<LoanEligibilityRuleResponse> getAllEligibilityRules() {
        return eligibilityService.getAllEligibilityRules();
    }

    // Get eligibility rule by rule ID
    @GetMapping("/rule/{loanEid}")
    public LoanEligibilityRuleResponse getEligibilityRuleById(@PathVariable UUID loanEid) {
        return eligibilityService.getEligibilityRuleById(loanEid);
    }

    // Get rules by loan type
    @GetMapping("/{loanTypeId}")
    public List<LoanEligibilityRuleResponse> getRulesByLoanType(@PathVariable UUID loanTypeId) {
        return eligibilityService.getRulesByLoanType(loanTypeId);
    }
}