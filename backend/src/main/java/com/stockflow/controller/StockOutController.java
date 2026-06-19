package com.stockflow.controller;

import com.stockflow.dto.request.StockOutRequest;
import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.PageResponse;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.service.StockOutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/stock-out")
public class StockOutController {

    private final StockOutService stockOutService;

    public StockOutController(StockOutService stockOutService) {
        this.stockOutService = stockOutService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StockTransactionResponse>> createStockOut(
            @RequestBody StockOutRequest request
    ) {
        StockTransactionResponse response = stockOutService.createStockOut(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Stock-out transaction created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StockTransactionResponse>>> getStockOutHistory(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        PageResponse<StockTransactionResponse> response = stockOutService.getStockOutHistory(
                productName,
                fromDate,
                toDate,
                page,
                size,
                sortBy,
                sortDirection
        );

        return ResponseEntity.ok(
                ApiResponse.success("Stock-out history retrieved successfully", response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockTransactionResponse>> getStockOutById(
            @PathVariable Long id
    ) {
        StockTransactionResponse response = stockOutService.getStockOutById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Stock-out transaction retrieved successfully", response)
        );
    }
}
