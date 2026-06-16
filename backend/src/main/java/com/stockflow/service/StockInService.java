package com.stockflow.service;

import com.stockflow.dto.request.StockInRequest;
import com.stockflow.dto.response.StockTransactionResponse;
import com.stockflow.entity.Product;
import com.stockflow.entity.StockIn;
import com.stockflow.enums.ProductStatus;
import com.stockflow.exception.BusinessException;
import com.stockflow.exception.ResourceNotFoundException;
import com.stockflow.repository.ProductRepository;
import com.stockflow.repository.StockInRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
}
