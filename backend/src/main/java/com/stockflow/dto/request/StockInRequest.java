package com.stockflow.dto.request;

public class StockInRequest {
    private Long productId;
    private Integer quantity;
    private String note;

    public StockInRequest() {
    }

    public StockInRequest(Long productId, Integer quantity, String note) {
        this.productId = productId;
        this.quantity = quantity;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
