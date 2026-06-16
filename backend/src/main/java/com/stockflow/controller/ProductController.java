package com.stockflow.controller;

import com.stockflow.dto.request.ProductRequest;
import com.stockflow.dto.response.ApiResponse;
import com.stockflow.dto.response.ProductResponse;
import com.stockflow.enums.ProductStatus;
import com.stockflow.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus status
    ) {
        List<ProductResponse> products = productService.getProducts(keyword, category, status);

        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(
                true,
                "Products retrieved successfully",
                products
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product retrieved successfully",
                product
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        ProductResponse product = productService.updateProduct(id, request);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product updated successfully",
                product
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
