package com.stockflow.service;

import com.stockflow.dto.request.StockOutRequest;
import com.stockflow.dto.response.PageResponse;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.entity.Product;
import com.stockflow.entity.StockOut;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import com.stockflow.repository.ProductRepository;
import com.stockflow.repository.StockOutRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockOutService {

    private final StockOutRepository stockOutRepository;
    private final ProductRepository productRepository;

    public StockOutService(StockOutRepository stockOutRepository, ProductRepository productRepository) {
        this.stockOutRepository = stockOutRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockTransactionResponse createStockOut(StockOutRequest request) {
        validateStockOutRequest(request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new BusinessException("Inactive product cannot be used for stock-out transaction");
        }

        if (request.getQuantity() > product.getQuantity()) {
            throw new BusinessException("Stock-out quantity cannot exceed current inventory quantity");
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());

        Long temporaryCreatedBy = 1L;

        StockOut stockOut = new StockOut(
                product,
                request.getQuantity(),
                request.getReason(),
                temporaryCreatedBy
        );

        StockOut savedStockOut = stockOutRepository.save(stockOut);
        productRepository.save(product);

        return mapToStockTransactionResponse(savedStockOut);
    }

    public PageResponse<StockTransactionResponse> getStockOutHistory(
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

        Page<StockTransactionResponse> stockOutPage = stockOutRepository.findAll((root, query, criteriaBuilder) -> {
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

        return PageResponse.from(stockOutPage);
    }

    private void validateStockOutRequest(StockOutRequest request) {
        if (request == null) {
            throw new BusinessException("Stock-out request is required");
        }

        if (request.getProductId() == null) {
            throw new BusinessException("Product is required");
        }

        if (request.getQuantity() == null) {
            throw new BusinessException("Quantity is required");
        }

        if (request.getQuantity() <= 0) {
            throw new BusinessException("Stock-out quantity must be greater than 0");
        }
    }

    private StockTransactionResponse mapToStockTransactionResponse(StockOut stockOut) {
        return new StockTransactionResponse(
                stockOut.getId(),
                stockOut.getProduct().getId(),
                stockOut.getProduct().getName(),
                stockOut.getQuantity(),
                null,
                stockOut.getReason(),
                stockOut.getCreatedBy(),
                stockOut.getCreatedAt()
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
