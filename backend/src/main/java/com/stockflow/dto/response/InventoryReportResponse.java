package com.stockflow.dto.response;

import com.stockflow.enums.StockStatus;

import java.time.LocalDateTime;

public class InventoryReportResponse {

    private Long productId;
    private String productName;
    private String category;
    private Integer quantity;
    private StockStatus stockStatus;
    private LocalDateTime lastUpdatedDate;

    public InventoryReportResponse() {
    }

    public InventoryReportResponse(
            Long productId,
            String productName,
            String category,
            Integer quantity,
            StockStatus stockStatus,
            LocalDateTime lastUpdatedDate
    ) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.stockStatus = stockStatus;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
