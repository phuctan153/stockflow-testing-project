import type { AuthUser } from '../types/auth';

const AUTH_USER_KEY = 'stockflow_auth_user';

export function saveAuthUser(user: AuthUser): void {
    localStorage.setItem(AUTH_USER_KEY, JSON.stringify(user));
}

export function getAuthUser(): AuthUser | null {
    const rawUser = localStorage.getItem(AUTH_USER_KEY);

    if (!rawUser) {
        return null;
    }

    try {
        return JSON.parse(rawUser) as AuthUser;
    } catch {
        localStorage.removeItem(AUTH_USER_KEY);
        return null;
    }
}

export function getAccessToken(): string | null {
    return getAuthUser()?.token ?? null;
}

export function clearAuthUser(): void {
    localStorage.removeItem(AUTH_USER_KEY);
}

export function isAuthenticated(): boolean {
    return Boolean(getAccessToken());
}

export function getUserRole() {
    return getAuthUser()?.role ?? null;
}