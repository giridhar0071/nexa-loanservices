package com.nexabank.loanservices.service;

import com.nexabank.loanservices.dto.LoanEligibilityRuleResponse;
import com.nexabank.loanservices.entity.LoanEligibilityRule;
import com.nexabank.loanservices.entity.LoanPoints;
import com.nexabank.loanservices.repository.LoanEligibilityRuleRepository;
import com.nexabank.loanservices.repository.LoanPointsRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanEligibilityRuleService {

    private final LoanEligibilityRuleRepository eligibilityRepo;
    private final LoanPointsRepository loanPointsRepo;

    public LoanEligibilityRuleService(LoanEligibilityRuleRepository eligibilityRepo,
                                      LoanPointsRepository loanPointsRepo) {
        this.eligibilityRepo = eligibilityRepo;
        this.loanPointsRepo = loanPointsRepo;
    }

    /**
     * Generate default eligibility rules for a loan type.
     * Rules:
     *  - 720–850 → APR = 10.5 (direct approval)
     *  - 630–719 → APR = 11.5 (direct approval)
     *  - 590–629 → linked to loan_points (APR null → system applies 12.5 + fee)
     *  - 550–589 → linked to loan_points
     *  - 500–549 → linked to loan_points
     *  - <500 → no rule (ineligible)
     */
    public List<LoanEligibilityRuleResponse> createDefaultEligibilityRules(UUID loanTypeId, Double minSalary) {
        // loanPoints must already exist for this loan type
        List<LoanPoints> loanPoints = loanPointsRepo.findByLoanTypeId(loanTypeId);
        if (loanPoints.isEmpty()) {
            throw new RuntimeException("LoanPoints not initialized for loanTypeId: " + loanTypeId);
        }

        Map<String, LoanPoints> pointsByRange = loanPoints.stream()
                .collect(Collectors.toMap(
                        lp -> lp.getMinCreditScore() + "-" + lp.getMaxCreditScore(),
                        lp -> lp
                ));

        List<LoanEligibilityRule> rules = List.of(
                buildRule(loanTypeId, 720, 850, 10.5, minSalary, null),
                buildRule(loanTypeId, 630, 719, 11.5, minSalary, null),
                buildRule(loanTypeId, 590, 629, null, minSalary, getLoanPointId(pointsByRange, "590-629")),
                buildRule(loanTypeId, 550, 589, null, minSalary, getLoanPointId(pointsByRange, "550-589")),
                buildRule(loanTypeId, 500, 549, null, minSalary, getLoanPointId(pointsByRange, "500-549"))
        );

        List<LoanEligibilityRule> saved = eligibilityRepo.saveAll(rules);
        return saved.stream().map(this::mapToResponse).toList();
    }

    // === Queries ===

    public List<LoanEligibilityRuleResponse> getAllEligibilityRules() {
        return eligibilityRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    public LoanEligibilityRuleResponse getEligibilityRuleById(UUID loanEid) {
        LoanEligibilityRule rule = eligibilityRepo.findById(loanEid)
                .orElseThrow(() -> new RuntimeException("Rule not found for ID: " + loanEid));
        return mapToResponse(rule);
    }

    public List<LoanEligibilityRuleResponse> getRulesByLoanType(UUID loanTypeId) {
        return eligibilityRepo.findByLoanTypeId(loanTypeId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // === Helpers ===

    private LoanEligibilityRule buildRule(UUID loanTypeId, int minScore, int maxScore,
                                          Double apr, Double minSalary, UUID loanPointId) {
        LoanEligibilityRule rule = new LoanEligibilityRule();
        rule.setLoanTypeId(loanTypeId);
        rule.setMinCreditScore(minScore);
        rule.setMaxCreditScore(maxScore);
        rule.setApr(apr);
        rule.setMinSalary(minSalary);
        rule.setLoanPointId(loanPointId);
        return rule;
    }

    private UUID getLoanPointId(Map<String, LoanPoints> pointsByRange, String range) {
        if (!pointsByRange.containsKey(range)) {
            throw new RuntimeException("Missing loan points tier for range: " + range);
        }
        return pointsByRange.get(range).getLoanPointId();
    }

    private LoanEligibilityRuleResponse mapToResponse(LoanEligibilityRule rule) {
        return new LoanEligibilityRuleResponse(
                rule.getLoanEid(),
                rule.getLoanTypeId(),
                rule.getMaxLoanAmount(),
                rule.getApr(),
                rule.getMinSalary(),
                rule.getMinCreditScore(),
                rule.getMaxCreditScore(),
                rule.getLoanPointId()
        );
    }
}
