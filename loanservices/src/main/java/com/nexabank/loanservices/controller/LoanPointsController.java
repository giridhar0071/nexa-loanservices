package com.nexabank.loanservices.controller;

import com.nexabank.loanservices.dto.LoanPointsResponse;
import com.nexabank.loanservices.service.LoanPointsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loanpoints")
public class LoanPointsController {

    private final LoanPointsService loanPointsService;

    public LoanPointsController(LoanPointsService loanPointsService) {
        this.loanPointsService = loanPointsService;
    }

    // Get all loan points
    @GetMapping
    public List<LoanPointsResponse> getAllLoanPoints() {
        return loanPointsService.getAllLoanPoints();
    }

    // Get loan points by loanTypeId
    @GetMapping("/{loanTypeId}")
    public List<LoanPointsResponse> getLoanPointsByLoanType(@PathVariable UUID loanTypeId) {
        return loanPointsService.getLoanPointsByLoanType(loanTypeId);
    }
}