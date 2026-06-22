import axiosClient from './axiosClient';
import type { ApiResponse } from '../types/api';
import type { LoginRequest, LoginResponse } from '../types/auth';

export async function loginApi(
    request: LoginRequest
): Promise<ApiResponse<LoginResponse>> {
    const response = await axiosClient.post<ApiResponse<LoginResponse>>(
        '/api/auth/login',
        request
    );

    return response.data;
}