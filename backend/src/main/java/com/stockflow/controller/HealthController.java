package com.stockflow.controller;

import com.stockflow.dto.response.ApiResponse;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ApiResponse<String> health() {
        return ApiResponse.success("Backend is running", "StockFlow API");
    }

    @GetMapping("/api/health/business-error")
    public ApiResponse<String> businessError() {
        throw new BusinessException("This is a business error");
    }

    @GetMapping("/api/health/not-found")
    public ApiResponse<String> notFound() {
        throw new ResourceNotFoundException("Resource not found");
    }
}
