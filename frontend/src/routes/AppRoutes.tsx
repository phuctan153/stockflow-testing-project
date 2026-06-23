import { Navigate, Route, Routes } from 'react-router-dom';
import AppLayout from '../components/AppLayout';
import ProtectedRoute from '../components/ProtectedRoute';
import DashboardPage from '../pages/DashboardPage';
import LoginPage from '../pages/LoginPage';
import PlaceholderPage from '../pages/PlaceholderPage';
import ProductListPage from "../pages/ProductListPage.tsx";
import AdminRoute from "../components/AdminRoute.tsx";
import ProductFormPage from "../pages/ProductFormPage.tsx";

function AppRoutes() {
    return (
        <Routes>
            <Route path="/login" element={<LoginPage />} />

            <Route
                path="/"
                element={
                    <ProtectedRoute>
                        <AppLayout />
                    </ProtectedRoute>
                }
            >
                <Route index element={<Navigate to="/dashboard" replace />} />
                <Route path="dashboard" element={<DashboardPage />} />
                <Route path="products" element={<ProductListPage />} />

                <Route
                    path="products/create"
                    element={
                        <AdminRoute>
                            <ProductFormPage />
                        </AdminRoute>
                    }
                />

                <Route
                    path="products/edit/:id"
                    element={
                        <AdminRoute>
                            <ProductFormPage />
                        </AdminRoute>
                    }
                />

                <Route
                    path="stock-in/create"
                    element={
                        <PlaceholderPage
                            title="Stock In"
                            description="Create stock-in transaction page will be implemented later."
                        />
                    }
                />

                <Route
                    path="stock-out/create"
                    element={
                        <PlaceholderPage
                            title="Stock Out"
                            description="Create stock-out transaction page will be implemented later."
                        />
                    }
                />

                <Route
                    path="stock-in/history"
                    element={
                        <PlaceholderPage
                            title="Stock-In History"
                            description="Stock-in transaction history page will be implemented later."
                        />
                    }
                />

                <Route
                    path="stock-out/history"
                    element={
                        <PlaceholderPage
                            title="Stock-Out History"
                            description="Stock-out transaction history page will be implemented later."
                        />
                    }
                />

                <Route
                    path="inventory/report"
                    element={
                        <PlaceholderPage
                            title="Inventory Report"
                            description="Inventory report page will be implemented later."
                        />
                    }
                />
            </Route>

            <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
    );
}

export default AppRoutes;