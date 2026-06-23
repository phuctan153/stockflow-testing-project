import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type {
    StockOutRequest,
    StockTransaction,
    StockTransactionPage,
} from '../types/stock';

interface StockOutSearchParams {
    productName?: string;
    fromDate?: string;
    toDate?: string;
    page?: number;
    size?: number;
    sortBy?: string;
    sortDirection?: 'asc' | 'desc';
}

export async function createStockOutApi(
    request: StockOutRequest
): Promise<ApiResponse<StockTransaction>> {
    const response = await axiosClient.post<ApiResponse<StockTransaction>>(
        '/api/stock-out',
        request
    );

    return response.data;
}

export async function getStockOutHistoryApi(
    params: StockOutSearchParams
): Promise<ApiResponse<StockTransactionPage>> {
    const response = await axiosClient.get<ApiResponse<StockTransactionPage>>(
        '/api/stock-out',
        {
            params,
        }
    );

    return response.data;
}

export async function getStockOutDetailApi(
    id: number
): Promise<ApiResponse<StockTransaction>> {
    const response = await axiosClient.get<ApiResponse<StockTransaction>>(
        `/api/stock-out/${id}`
    );

    return response.data;
}