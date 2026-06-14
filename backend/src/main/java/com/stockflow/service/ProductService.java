package com.stockflow.service;

import com.stockflow.dto.request.ProductRequest;
import com.stockflow.dto.response.ProductResponse;
import com.stockflow.entity.Product;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        validateCreateProductRequest(request);

        Product product = new Product();
        product.setName(request.getName().trim());
        product.setCategory(request.getCategory().trim());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity() == null ? 0 : request.getQuantity());
        product.setStatus(request.getStatus() == null ? ProductStatus.ACTIVE : request.getStatus());

        Product savedProduct = productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    private void validateCreateProductRequest(ProductRequest request) {
        if (request == null) {
            throw new BusinessException("Product request is required");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessException("Product name is required");
        }

        if (productRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new BusinessException("Product name must be unique");
        }

        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new BusinessException("Category is required");
        }

        if (request.getPrice() == null) {
            throw new BusinessException("Price is required");
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Price must be greater than 0");
        }

        if (request.getQuantity() != null && request.getQuantity() < 0) {
            throw new BusinessException("Quantity cannot be negative");
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
