import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type { Product, ProductPage, ProductSearchParams } from '../types/product';

export async function getProductsApi(
    params: ProductSearchParams
): Promise<ApiResponse<ProductPage>> {
    const response = await axiosClient.get<ApiResponse<ProductPage>>(
        '/api/products',
        {
            params,
        }
    );

    return response.data;
}

export async function getProductDetailApi(
    id: number
): Promise<ApiResponse<Product>> {
    const response = await axiosClient.get<ApiResponse<Product>>(
        `/api/products/${id}`
    );

    return response.data;
}

export async function deleteProductApi(
    id: number
): Promise<ApiResponse<null>> {
    const response = await axiosClient.delete<ApiResponse<null>>(
        `/api/products/${id}`
    );

    return response.data;
}