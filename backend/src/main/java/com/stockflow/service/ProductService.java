package com.stockflow.service;

import com.stockflow.dto.request.ProductRequest;
import com.stockflow.dto.response.ProductResponse;
import com.stockflow.entity.Product;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import com.stockflow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public List<ProductResponse> getProducts(String keyword, String category, ProductStatus status) {
        List<Product> products;

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = category != null && !category.trim().isEmpty();
        boolean hasStatus = status != null;

        if (hasKeyword && hasCategory && hasStatus) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCaseAndStatus(
                    keyword.trim(),
                    category.trim(),
                    status
            );
        } else if (hasKeyword && hasCategory) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(
                    keyword.trim(),
                    category.trim()
            );
        } else if (hasKeyword && hasStatus) {
            products = productRepository.findByNameContainingIgnoreCaseAndStatus(
                    keyword.trim(),
                    status
            );
        } else if (hasCategory && hasStatus) {
            products = productRepository.findByCategoryIgnoreCaseAndStatus(
                    category.trim(),
                    status
            );
        } else if (hasKeyword) {
            products = productRepository.findByNameContainingIgnoreCase(keyword.trim());
        } else if (hasCategory) {
            products = productRepository.findByCategoryIgnoreCase(category.trim());
        } else if (hasStatus) {
            products = productRepository.findByStatus(status);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        return mapToProductResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        validateUpdateProductRequest(id, request);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(request.getName().trim());
        product.setCategory(request.getCategory().trim());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus() == null ? ProductStatus.ACTIVE : request.getStatus());

        Product updatedProduct = productRepository.save(product);

        return mapToProductResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (product.getQuantity() > 0) {
            throw new BusinessException("Cannot delete product because it has inventory quantity or transaction history");
        }

        productRepository.delete(product);
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

    private void validateUpdateProductRequest(Long id, ProductRequest request) {
        if (request == null) {
            throw new BusinessException("Product request is required");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessException("Product name is required");
        }

        Product existingProductWithSameName = productRepository.findByNameIgnoreCase(request.getName().trim())
                .orElse(null);

        if (existingProductWithSameName != null && !existingProductWithSameName.getId().equals(id)) {
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

        if (request.getStatus() == null) {
            throw new BusinessException("Product status is required");
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
