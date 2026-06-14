package com.stockflow.dto.request;

import com.stockflow.enums.ProductStatus;

import java.math.BigDecimal;

public class ProductRequest {

    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private ProductStatus status;

    public ProductRequest() {
    }

    public ProductRequest(String name, String category, BigDecimal price, Integer quantity, ProductStatus status) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
