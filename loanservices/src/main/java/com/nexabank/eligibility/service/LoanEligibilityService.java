package com.nexabank.eligibility.service;

import com.nexabank.eligibility.dto.LoanEligibilityCheckRequest;
import com.nexabank.eligibility.dto.LoanEligibilityCheckResponse;
import com.nexabank.eligibility.entity.LoanEligibilityRule;
import com.nexabank.eligibility.repository.LoanEligibilityRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanEligibilityService {

    private final LoanEligibilityRuleRepository repository;

    public LoanEligibilityCheckResponse checkEligibility(LoanEligibilityCheckRequest request,
                                                         String loanTypeName,    // fetched from Loan Type service
                                                         Integer creditScore,    // fetched from User service
                                                         java.math.BigDecimal annualSalary) {

        List<String> reasons = new ArrayList<>();

        // 1. Find applicable rule based on credit score & loan type
        LoanEligibilityRule rule = repository
                .findFirstByLoanTypeIdAndMinCreditScoreLessThanEqualAndMaxCreditScoreGreaterThanEqual(
                        request.getLoanTypeId(), creditScore, creditScore);

        if (rule == null) {
            reasons.add("No eligibility rule found for given score range.");
            return LoanEligibilityCheckResponse.builder()
                    .eligible(false)
                    .loanType(loanTypeName)
                    .reasons(reasons)
                    .build();
        }

        // 2. Salary check
        if (annualSalary.compareTo(rule.getMinSalary()) < 0) {
            reasons.add("Salary below required minimum.");
            return LoanEligibilityCheckResponse.builder()
                    .eligible(false)
                    .loanType(loanTypeName)
                    .reasons(reasons)
                    .build();
        }

        // 3. Direct approval if APR exists
        if (rule.isDirectApproval()) {
            return LoanEligibilityCheckResponse.builder()
                    .eligible(true)
                    .loanType(loanTypeName)
                    .apr(rule.getApr())
                    .requiresLoanPoints(false)
                    .loanPointId(null)
                    .sanctionedAmount(
                            rule.getMaxLoanAmount() != null
                                    ? request.getRequestedAmount().min(rule.getMaxLoanAmount())
                                    : request.getRequestedAmount()
                    )
                    .reasons(reasons)
                    .build();
        }


        // 4. Requires loan points
        if (rule.requiresLoanPoints()) {
            return LoanEligibilityCheckResponse.builder()
                    .eligible(true)
                    .loanType(loanTypeName)
                    .apr(null)
                    .requiresLoanPoints(true)
                    .loanPointId(rule.getLoanPointId())
                    .sanctionedAmount(
                            rule.getMaxLoanAmount() != null
                                    ? request.getRequestedAmount().min(rule.getMaxLoanAmount())
                                    : request.getRequestedAmount()
                    )
                    .reasons(List.of("Requires loan points for approval."))
                    .build();
        }


        // 5. Default reject
        reasons.add("Applicant not eligible based on configured rules.");
        return LoanEligibilityCheckResponse.builder()
                .eligible(false)
                .loanType(loanTypeName)
                .reasons(reasons)
                .build();
    }
}
