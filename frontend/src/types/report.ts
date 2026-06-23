export type StockStatus = 'IN_STOCK' | 'LOW_STOCK' | 'OUT_OF_STOCK';

export interface InventoryReportItem {
    productId: number;
    productName: string;
    category: string;
    quantity: number;
    stockStatus: StockStatus;
    lastUpdatedDate: string;
}

export interface InventoryReportSearchParams {
    category?: string;
    stockStatus?: StockStatus;
}