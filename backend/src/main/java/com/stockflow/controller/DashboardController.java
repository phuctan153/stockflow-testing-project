package com.stockflow.controller;

import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.DashboardSummaryResponse;
import com.stockflow.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary() {
        DashboardSummaryResponse response = dashboardService.getDashboardSummary();

        return ResponseEntity.ok(
                ApiResponse.success("Dashboard summary retrieved successfully", response)
        );
    }
}
