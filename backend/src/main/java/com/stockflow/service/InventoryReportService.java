package com.stockflow.service;

import com.stockflow.dto.response.InventoryReportResponse;
import com.stockflow.entity.Product;
import com.stockflow.enums.StockStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryReportService {

    private final ProductRepository productRepository;

    public InventoryReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<InventoryReportResponse> getInventoryReport(String category, String stockStatus) {
        StockStatus parsedStockStatus = parseStockStatus(stockStatus);

        List<Product> products = productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null && !category.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("category")),
                                category.trim().toLowerCase()
                        )
                );
            }

            if (parsedStockStatus != null) {
                switch (parsedStockStatus) {
                    case IN_STOCK:
                        predicates.add(criteriaBuilder.greaterThan(root.get("quantity"), 10));
                        break;
                    case LOW_STOCK:
                        predicates.add(criteriaBuilder.between(root.get("quantity"), 1, 10));
                        break;
                    case OUT_OF_STOCK:
                        predicates.add(criteriaBuilder.equal(root.get("quantity"), 0));
                        break;
                    default:
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return products.stream()
                .map(this::mapToInventoryReportResponse)
                .toList();
    }

    private InventoryReportResponse mapToInventoryReportResponse(Product product) {
        return new InventoryReportResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getQuantity(),
                determineStockStatus(product.getQuantity()),
                getLastUpdatedDate(product)
        );
    }

    private StockStatus determineStockStatus(Integer quantity) {
        if (quantity == null || quantity == 0) {
            return StockStatus.OUT_OF_STOCK;
        }

        if (quantity <= 10) {
            return StockStatus.LOW_STOCK;
        }

        return StockStatus.IN_STOCK;
    }

    private LocalDateTime getLastUpdatedDate(Product product) {
        if (product.getUpdatedAt() != null) {
            return product.getUpdatedAt();
        }

        return product.getCreatedAt();
    }

    private StockStatus parseStockStatus(String stockStatus) {
        if (stockStatus == null || stockStatus.trim().isEmpty()) {
            return null;
        }

        try {
            return StockStatus.valueOf(stockStatus.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Invalid stock status. Allowed values are IN_STOCK, LOW_STOCK, OUT_OF_STOCK");
        }
    }
}
