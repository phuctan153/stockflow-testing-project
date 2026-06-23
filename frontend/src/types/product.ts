export type ProductStatus = 'ACTIVE' | 'INACTIVE';

export interface Product {
    id: number;
    name: string;
    category: string;
    price: number;
    quantity: number;
    status: ProductStatus;
    createdAt: string;
    updatedAt: string;
}

export interface ProductPage {
    content: Product[];
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    last: boolean;
}

export interface ProductSearchParams {
    keyword?: string;
    category?: string;
    status?: ProductStatus;
    page?: number;
    size?: number;
    sortBy?: string;
    sortDirection?: 'asc' | 'desc';
}

export interface CreateProductRequest {
    name: string;
    category: string;
    price: number;
    quantity: number;
    status: ProductStatus;
}

export interface UpdateProductRequest {
    name: string;
    category: string;
    price: number;
    status: ProductStatus;
}