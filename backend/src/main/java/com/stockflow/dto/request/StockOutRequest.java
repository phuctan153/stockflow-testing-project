package com.stockflow.dto.request;

public class StockOutRequest {
    private Long productId;
    private Integer quantity;
    private String reason;

    public StockOutRequest() {
    }

    public StockOutRequest(Long productId, Integer quantity, String reason) {
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
