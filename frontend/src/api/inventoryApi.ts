import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type {
    InventoryReportItem,
    InventoryReportSearchParams,
} from '../types/report';

export async function getInventoryReportApi(
    params: InventoryReportSearchParams
): Promise<ApiResponse<InventoryReportItem[]>> {
    const response = await axiosClient.get<ApiResponse<InventoryReportItem[]>>(
        '/api/inventory/report',
        {
            params,
        }
    );

    return response.data;
}