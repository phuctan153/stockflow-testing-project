package com.stockflow.controller;

import com.stockflow.dto.request.StockInRequest;
import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.PageResponse;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.service.StockInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StockTransactionResponse>>> getStockInHistory(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        PageResponse<StockTransactionResponse> response = stockInService.getStockInHistory(
                productName,
                fromDate,
                toDate,
                page,
                size,
                sortBy,
                sortDirection
        );

        return ResponseEntity.ok(
                ApiResponse.success("Stock-in history retrieved successfully", response)
        );
    }
}
