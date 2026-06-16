package com.stockflow.dto.response;

import java.time.LocalDateTime;

public class StockTransactionResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String note;
    private String reason;
    private Long createdBy;
    private LocalDateTime createdAt;

    public StockTransactionResponse() {
    }

    public StockTransactionResponse(
            Long id,
            Long productId,
            String productName,
            Integer quantity,
            String note,
            String reason,
            Long createdBy,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.note = note;
        this.reason = reason;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getNote() {
        return note;
    }

    public String getReason() {
        return reason;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
