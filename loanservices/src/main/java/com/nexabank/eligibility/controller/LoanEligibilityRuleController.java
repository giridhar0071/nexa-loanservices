package com.nexabank.eligibility.controller;

import com.nexabank.eligibility.dto.LoanEligibilityRuleRequest;
import com.nexabank.eligibility.dto.LoanEligibilityRuleResponse;
import com.nexabank.eligibility.service.LoanEligibilityRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eligibility/rules")
@RequiredArgsConstructor
public class LoanEligibilityRuleController {

    private final LoanEligibilityRuleService ruleService;

    //  Add a new rule
    @PostMapping
    public ResponseEntity<LoanEligibilityRuleResponse> addRule(
            @RequestBody LoanEligibilityRuleRequest request) {
        return ResponseEntity.ok(ruleService.addRule(request));
    }

    //  Get all rules
    @GetMapping
    public ResponseEntity<List<LoanEligibilityRuleResponse>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    //  Get rule by ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanEligibilityRuleResponse> getRuleById(@PathVariable("id") UUID ruleId) {
        return ResponseEntity.ok(ruleService.getRuleById(ruleId));
    }

    //  Delete a rule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") UUID ruleId) {
        ruleService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }
}
