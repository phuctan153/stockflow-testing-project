package com.stockflow.controller;

import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.InventoryReportResponse;
import com.stockflow.service.InventoryReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryReportController {

    private final InventoryReportService inventoryReportService;

    public InventoryReportController(InventoryReportService inventoryReportService) {
        this.inventoryReportService = inventoryReportService;
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<List<InventoryReportResponse>>> getInventoryReport(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String stockStatus
    ) {
        List<InventoryReportResponse> response = inventoryReportService.getInventoryReport(category, stockStatus);

        return ResponseEntity.ok(
                ApiResponse.success("Inventory report retrieved successfully", response)
        );
    }
}
