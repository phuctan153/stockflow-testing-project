import type { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { getAuthUser } from '../utils/authStorage';

interface AdminRouteProps {
    children: ReactNode;
}

function AdminRoute({ children }: AdminRouteProps) {
    const authUser = getAuthUser();

    if (authUser?.role !== 'ADMIN') {
        return <Navigate to="/dashboard" replace />;
    }

    return children;
}

export default AdminRoute;