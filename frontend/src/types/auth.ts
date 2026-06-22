export type UserRole = 'ADMIN' | 'STAFF';

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    userId: number;
    username: string;
    fullName: string;
    role: UserRole;
    token: string;
}

export interface AuthUser {
    userId: number;
    username: string;
    fullName: string;
    role: UserRole;
    token: string;
}