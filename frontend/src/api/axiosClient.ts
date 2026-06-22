import axios from 'axios';
import { clearAuthUser, getAccessToken } from '../utils/authStorage';

const axiosClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosClient.interceptors.request.use((config) => {
    const token = getAccessToken();

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

axiosClient.interceptors.response.use(
    (response) => response,
    (error) => {
        const status = error.response?.status;

        if (status === 401) {
            clearAuthUser();
            window.location.href = '/login';
        }

        return Promise.reject(error);
    }
);

export default axiosClient;