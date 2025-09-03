package com.nexabank.eligibility.service;

import com.nexabank.eligibility.dto.LoanEligibilityRuleRequest;
import com.nexabank.eligibility.dto.LoanEligibilityRuleResponse;
import com.nexabank.eligibility.entity.LoanEligibilityRule;
import com.nexabank.eligibility.repository.LoanEligibilityRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanEligibilityRuleService {

    private final LoanEligibilityRuleRepository repository;

    // Add new rule
    public LoanEligibilityRuleResponse addRule(LoanEligibilityRuleRequest request) {
        LoanEligibilityRule rule = LoanEligibilityRule.builder()
                .loanTypeId(request.getLoanTypeId())
                .maxLoanAmount(request.getMaxLoanAmount())
                .apr(request.getApr())
                .minSalary(request.getMinSalary())
                .minCreditScore(request.getMinCreditScore())
                .maxCreditScore(request.getMaxCreditScore())
                .loanPointId(request.getLoanPointId())
                .build();

        LoanEligibilityRule saved = repository.save(rule);
        return mapToResponse(saved);
    }

    // Get all rules
    public List<LoanEligibilityRuleResponse> getAllRules() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get rule by ID
    public LoanEligibilityRuleResponse getRuleById(UUID ruleId) {
        return repository.findById(ruleId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Rule not found with ID: " + ruleId));
    }

    //  Delete rule
    public void deleteRule(UUID ruleId) {
        repository.deleteById(ruleId);
    }

    //  Entity → Response DTO
    private LoanEligibilityRuleResponse mapToResponse(LoanEligibilityRule rule) {
        return LoanEligibilityRuleResponse.builder()
                .loanEid(rule.getLoanEid())
                .loanTypeId(rule.getLoanTypeId())
                .maxLoanAmount(rule.getMaxLoanAmount())
                .apr(rule.getApr())
                .minSalary(rule.getMinSalary())
                .minCreditScore(rule.getMinCreditScore())
                .maxCreditScore(rule.getMaxCreditScore())
                .loanPointId(rule.getLoanPointId())
                .build();
    }
}
