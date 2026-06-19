package com.stockflow.service;

import com.stockflow.dto.request.StockInRequest;
import com.stockflow.dto.response.PageResponse;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.entity.Product;
import com.stockflow.entity.StockIn;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import com.stockflow.repository.ProductRepository;
import com.stockflow.repository.StockInRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockInService {
    private final StockInRepository stockInRepository;
    private final ProductRepository productRepository;

    public StockInService(StockInRepository stockInRepository, ProductRepository productRepository) {
        this.stockInRepository = stockInRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockTransactionResponse createStockIn(StockInRequest request) {
        validateStockInRequest(request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new BusinessException("Inactive product cannot be used for stock-in transaction");
        }

        product.setQuantity(product.getQuantity() + request.getQuantity());

        Long temporaryCreatedBy = 1L;

        StockIn stockIn = new StockIn(
                product,
                request.getQuantity(),
                request.getNote(),
                temporaryCreatedBy
        );

        StockIn savedStockIn = stockInRepository.save(stockIn);
        productRepository.save(product);

        return mapToStockTransactionResponse(savedStockIn);
    }

    public PageResponse<StockTransactionResponse> getStockInHistory(
            String productName,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        validatePagination(page, size);

        Sort sort = buildSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);

        LocalDateTime fromDateTime = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime toDateTime = toDate != null ? toDate.atTime(23, 59, 59) : null;

        Page<StockTransactionResponse> stockInPage = stockInRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productName != null && !productName.trim().isEmpty()) {
                Join<Object, Object> productJoin = root.join("product");

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(productJoin.get("name")),
                                "%" + productName.trim().toLowerCase() + "%"
                        )
                );
            }

            if (fromDateTime != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDateTime));
            }

            if (toDateTime != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDateTime));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(this::mapToStockTransactionResponse);

        return PageResponse.from(stockInPage);
    }

    public StockTransactionResponse getStockInById(Long id) {
        StockIn stockIn = stockInRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock-in transaction not found with id: " + id));

        return mapToStockTransactionResponse(stockIn);
    }

    private void validateStockInRequest(StockInRequest request) {
        if (request == null) {
            throw new BusinessException("Stock-in request is required");
        }

        if (request.getProductId() == null) {
            throw new BusinessException("Product is required");
        }

        if (request.getQuantity() == null) {
            throw new BusinessException("Quantity is required");
        }

        if (request.getQuantity() <= 0) {
            throw new BusinessException("Stock-in quantity must be greater than 0");
        }
    }

    private StockTransactionResponse mapToStockTransactionResponse(StockIn stockIn) {
        return new StockTransactionResponse(
                stockIn.getId(),
                stockIn.getProduct().getId(),
                stockIn.getProduct().getName(),
                stockIn.getQuantity(),
                stockIn.getNote(),
                null,
                stockIn.getCreatedBy(),
                stockIn.getCreatedAt()
        );
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
                "quantity",
                "createdAt",
                "createdBy"
        );

        if (!allowedSortFields.contains(selectedSortBy)) {
            throw new BusinessException("Invalid sort field. Allowed values are id, quantity, createdAt, createdBy");
        }

        return selectedSortBy;
    }
}
