package com.nexabank.loanservices.service;

import com.nexabank.loanservices.dto.LoanPointsResponse;
import com.nexabank.loanservices.entity.LoanPoints;
import com.nexabank.loanservices.repository.LoanPointsRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoanPointsService {

    private final LoanPointsRepository loanPointsRepository;

    public LoanPointsService(LoanPointsRepository loanPointsRepository) {
        this.loanPointsRepository = loanPointsRepository;
    }

    /**
     * Pre-populate loan points rules for a given loan type
     */
    public List<LoanPointsResponse> createDefaultLoanPoints(UUID loanTypeId) {
        // Define fixed tiers
        List<LoanPoints> tiers = List.of(
                buildLoanPoints(loanTypeId, 500, 549, 0.08),
                buildLoanPoints(loanTypeId, 550, 589, 0.06),
                buildLoanPoints(loanTypeId, 590, 629, 0.04)
        );

        List<LoanPoints> saved = loanPointsRepository.saveAll(tiers);

        return saved.stream()
                .map(lp -> new LoanPointsResponse(
                        lp.getLoanPointId(),
                        lp.getLoanTypeId(),
                        lp.getMaxLoanAmount(),
                        lp.getPercentageOnLoanApproval(),
                        lp.getMinCreditScore(),
                        lp.getMaxCreditScore()
                )).toList();
    }

    public List<LoanPointsResponse> getLoanPointsByLoanType(UUID loanTypeId) {
        return loanPointsRepository.findByLoanTypeId(loanTypeId)
                .stream()
                .map(lp -> new LoanPointsResponse(
                        lp.getLoanPointId(),
                        lp.getLoanTypeId(),
                        lp.getMaxLoanAmount(),
                        lp.getPercentageOnLoanApproval(),
                        lp.getMinCreditScore(),
                        lp.getMaxCreditScore()
                ))
                .toList();
    }

    public List<LoanPointsResponse> getAllLoanPoints() {
        return loanPointsRepository.findAll()
                .stream()
                .map(lp -> new LoanPointsResponse(
                        lp.getLoanPointId(),
                        lp.getLoanTypeId(),
                        lp.getMaxLoanAmount(),
                        lp.getPercentageOnLoanApproval(),
                        lp.getMinCreditScore(),
                        lp.getMaxCreditScore()
                ))
                .toList();
    }

    private LoanPoints buildLoanPoints(UUID loanTypeId, int minScore, int maxScore, double percent) {
        LoanPoints lp = new LoanPoints();
        lp.setLoanTypeId(loanTypeId);
        lp.setMinCreditScore(minScore);
        lp.setMaxCreditScore(maxScore);
        lp.setPercentageOnLoanApproval(percent);
        lp.setMaxLoanAmount(null); // can be set later
        return lp;
    }
}
