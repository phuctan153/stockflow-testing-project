package com.stockflow.dto.response;

public class DashboardSummaryResponse {

    private long totalProducts;
    private long activeProducts;
    private long inactiveProducts;
    private long totalStockQuantity;
    private long lowStockProducts;
    private long outOfStockProducts;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(
            long totalProducts,
            long activeProducts,
            long inactiveProducts,
            long totalStockQuantity,
            long lowStockProducts,
            long outOfStockProducts
    ) {
        this.totalProducts = totalProducts;
        this.activeProducts = activeProducts;
        this.inactiveProducts = inactiveProducts;
        this.totalStockQuantity = totalStockQuantity;
        this.lowStockProducts = lowStockProducts;
        this.outOfStockProducts = outOfStockProducts;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getActiveProducts() {
        return activeProducts;
    }

    public void setActiveProducts(long activeProducts) {
        this.activeProducts = activeProducts;
    }

    public long getInactiveProducts() {
        return inactiveProducts;
    }

    public void setInactiveProducts(long inactiveProducts) {
        this.inactiveProducts = inactiveProducts;
    }

    public long getTotalStockQuantity() {
        return totalStockQuantity;
    }

    public void setTotalStockQuantity(long totalStockQuantity) {
        this.totalStockQuantity = totalStockQuantity;
    }

    public long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public long getOutOfStockProducts() {
        return outOfStockProducts;
    }

    public void setOutOfStockProducts(long outOfStockProducts) {
        this.outOfStockProducts = outOfStockProducts;
    }
}
