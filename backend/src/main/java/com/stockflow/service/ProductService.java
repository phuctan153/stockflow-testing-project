package com.stockflow.service;

import com.stockflow.dto.request.ProductRequest;
import com.stockflow.dto.response.PageResponse;
import com.stockflow.dto.response.ProductResponse;
import com.stockflow.entity.Product;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import com.stockflow.repository.ProductRepository;
import com.stockflow.repository.StockInRepository;
import com.stockflow.repository.StockOutRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockInRepository stockInRepository;
    private final StockOutRepository stockOutRepository;

    public ProductService(ProductRepository productRepository, StockInRepository stockInRepository, StockOutRepository stockOutRepository) {
        this.productRepository = productRepository;
        this.stockInRepository = stockInRepository;
        this.stockOutRepository = stockOutRepository;
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

    public PageResponse<ProductResponse> getProducts(
            String keyword,
            String category,
            String status,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        validatePagination(page, size);

        ProductStatus productStatus = parseProductStatus(status);

        Sort sort = buildSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductResponse> productPage = productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + keyword.trim().toLowerCase() + "%"
                        )
                );
            }

            if (category != null && !category.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("category")),
                                category.trim().toLowerCase()
                        )
                );
            }

            if (productStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), productStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(this::mapToProductResponse);

        return PageResponse.from(productPage);
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

        boolean hasStockInHistory = stockInRepository.existsByProduct_Id(id);
        boolean hasStockOutHistory = stockOutRepository.existsByProduct_Id(id);

        if (product.getQuantity() > 0 || hasStockInHistory || hasStockOutHistory) {
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

    private ProductStatus parseProductStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return null;
        }

        try {
            return ProductStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Invalid product status. Allowed values are ACTIVE or INACTIVE");
        }
    }

    private void validatePagination(int page, int size) {
        if (page < 0) {
            throw new BusinessException("Page number must be greater than or equal to 0");
        }

        if (size <= 0) {
            throw new BusinessException("Page size must be greater than 0");
        }

        if (size > 100) {
            throw new BusinessException("Page size must not exceed 100");
        }
    }

    private Sort buildSort(String sortBy, String sortDirection) {
        String selectedSortBy = normalizeSortBy(sortBy);

        Sort.Direction direction;

        if (sortDirection == null || sortDirection.trim().isEmpty()) {
            direction = Sort.Direction.DESC;
        } else {
            try {
                direction = Sort.Direction.fromString(sortDirection.trim());
            } catch (IllegalArgumentException exception) {
                throw new BusinessException("Invalid sort direction. Allowed values are asc or desc");
            }
        }

        return Sort.by(direction, selectedSortBy);
    }

    private String normalizeSortBy(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "createdAt";
        }

        String selectedSortBy = sortBy.trim();

        List<String> allowedSortFields = List.of(
                "id",
                "name",
                "category",
                "price",
                "quantity",
                "status",
                "createdAt",
                "updatedAt"
        );

        if (!allowedSortFields.contains(selectedSortBy)) {
            throw new BusinessException("Invalid sort field. Allowed values are id, name, category, price, quantity, status, createdAt, updatedAt");
        }

        return selectedSortBy;
    }
}
