export interface StockInRequest {
    productId: number;
    quantity: number;
    note?: string;
}

export interface StockOutRequest {
    productId: number;
    quantity: number;
    reason?: string;
}

export interface StockTransaction {
    id: number;
    productId: number;
    productName: string;
    quantity: number;
    note?: string | null;
    reason?: string | null;
    createdBy: number;
    createdAt: string;
}

export interface StockTransactionPage {
    content: StockTransaction[];
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    last: boolean;
}