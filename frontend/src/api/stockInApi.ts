import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type {
    StockInRequest,
    StockTransaction,
    StockTransactionPage,
} from '../types/stock';

interface StockInSearchParams {
    productName?: string;
    fromDate?: string;
    toDate?: string;
    page?: number;
    size?: number;
    sortBy?: string;
    sortDirection?: 'asc' | 'desc';
}

export async function createStockInApi(
    request: StockInRequest
): Promise<ApiResponse<StockTransaction>> {
    const response = await axiosClient.post<ApiResponse<StockTransaction>>(
        '/api/stock-in',
        request
    );

    return response.data;
}

export async function getStockInHistoryApi(
    params: StockInSearchParams
): Promise<ApiResponse<StockTransactionPage>> {
    const response = await axiosClient.get<ApiResponse<StockTransactionPage>>(
        '/api/stock-in',
        {
            params,
        }
    );

    return response.data;
}

export async function getStockInDetailApi(
    id: number
): Promise<ApiResponse<StockTransaction>> {
    const response = await axiosClient.get<ApiResponse<StockTransaction>>(
        `/api/stock-in/${id}`
    );

    return response.data;
}