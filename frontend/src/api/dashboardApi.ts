import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type { DashboardSummary } from '../types/dashboard';

export async function getDashboardSummaryApi(): Promise<
    ApiResponse<DashboardSummary>
> {
    const response = await axiosClient.get<ApiResponse<DashboardSummary>>(
        '/api/dashboard/summary'
    );

    return response.data;
}