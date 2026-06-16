package com.stockflow.controller;

import com.stockflow.dto.request.StockInRequest;
import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.service.StockInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock-in")
public class StockInController {
    private final StockInService stockInService;

    public StockInController(StockInService stockInService) {
        this.stockInService = stockInService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StockTransactionResponse>> createStockIn(
            @RequestBody StockInRequest request
    ) {
        StockTransactionResponse response = stockInService.createStockIn(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Stock-in transaction created successfully", response));
    }
}
