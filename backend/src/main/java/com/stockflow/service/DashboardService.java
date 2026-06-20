package com.stockflow.service;

import com.stockflow.dto.response.DashboardSummaryResponse;
import com.stockflow.enums.ProductStatus;
import com.stockflow.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ProductRepository productRepository;

    public DashboardService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public DashboardSummaryResponse getDashboardSummary() {
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.countByStatus(ProductStatus.ACTIVE);
        long inactiveProducts = productRepository.countByStatus(ProductStatus.INACTIVE);
        long totalStockQuantity = productRepository.sumTotalQuantity();
        long lowStockProducts = productRepository.countByQuantityBetween(1, 10);
        long outOfStockProducts = productRepository.countByQuantity(0);

        return new DashboardSummaryResponse(
                totalProducts,
                activeProducts,
                inactiveProducts,
                totalStockQuantity,
                lowStockProducts,
                outOfStockProducts
        );
    }
}
