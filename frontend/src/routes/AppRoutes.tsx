import { Navigate, Route, Routes } from 'react-router-dom';
import AppLayout from '../components/AppLayout';
import ProtectedRoute from '../components/ProtectedRoute';
import DashboardPage from '../pages/DashboardPage';
import LoginPage from '../pages/LoginPage';
import ProductListPage from "../pages/ProductListPage.tsx";
import AdminRoute from "../components/AdminRoute.tsx";
import ProductFormPage from "../pages/ProductFormPage.tsx";
import StockInPage from "../pages/StockInPage.tsx";
import StockOutPage from "../pages/StockOutPage.tsx";
import StockOutHistoryPage from "../pages/StockOutHistoryPage.tsx";
import StockInHistoryPage from "../pages/StockInHistoryPage.tsx";
import InventoryReportPage from "../pages/InventoryReportPage.tsx";

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

                <Route path="stock-in/create" element={<StockInPage />} />
                <Route path="stock-out/create" element={<StockOutPage />} />
                <Route path="stock-in/history" element={<StockInHistoryPage />} />
                <Route path="stock-out/history" element={<StockOutHistoryPage />} />
                <Route path="inventory/report" element={<InventoryReportPage />} />

            </Route>

            <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
    );
}

export default AppRoutes;