package com.stockflow.controller;

import com.stockflow.dto.request.ProductRequest;
import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.ProductResponse;
import com.stockflow.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.createProduct(request);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product created successfully",
                productResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
