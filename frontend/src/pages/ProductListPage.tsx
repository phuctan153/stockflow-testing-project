import {
    Button,
    Card,
    Input,
    message,
    Modal,
    Select,
    Space,
    Table,
    Typography,
} from 'antd';
import type { ColumnsType } from 'antd/es/table';
import {
    DeleteOutlined,
    EditOutlined,
    PlusOutlined,
    ReloadOutlined,
    SearchOutlined,
} from '@ant-design/icons';
import { useEffect, useMemo, useState } from 'react';
import { deleteProductApi, getProductsApi } from '../api/productApi';
import StatusTag from '../components/StatusTag';
import type { Product, ProductStatus } from '../types/product';
import { formatCurrency } from '../utils/formatCurrency';
import { formatDateTime } from '../utils/formatDate';
import { getAuthUser } from '../utils/authStorage';
import { useNavigate } from 'react-router-dom';

const { Title, Text } = Typography;

function ProductListPage() {
    const authUser = getAuthUser();
    const isAdmin = authUser?.role === 'ADMIN';
    const navigate = useNavigate();

    const [products, setProducts] = useState<Product[]>([]);
    const [keyword, setKeyword] = useState('');
    const [category, setCategory] = useState<string | undefined>();
    const [status, setStatus] = useState<ProductStatus | undefined>();
    const [loading, setLoading] = useState(false);

    const [pageNumber, setPageNumber] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [totalElements, setTotalElements] = useState(0);

    const categoryOptions = useMemo(() => {
        const uniqueCategories = Array.from(
            new Set(products.map((product) => product.category).filter(Boolean))
        );

        return uniqueCategories.map((item) => ({
            label: item,
            value: item,
        }));
    }, [products]);

    const fetchProducts = async () => {
        try {
            setLoading(true);

            const response = await getProductsApi({
                keyword: keyword || undefined,
                category,
                status,
                page: pageNumber - 1,
                size: pageSize,
                sortBy: 'createdAt',
                sortDirection: 'desc',
            });

            if (!response.success) {
                message.error(response.message || 'Failed to load products');
                return;
            }

            setProducts(response.data.content);
            setTotalElements(response.data.totalElements);
        } catch (error: any) {
            const errorMessage =
                error.response?.data?.message || 'Failed to load products';

            message.error(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProducts();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pageNumber, pageSize]);

    const handleSearch = () => {
        setPageNumber(1);
        fetchProducts();
    };

    const handleReset = () => {
        setKeyword('');
        setCategory(undefined);
        setStatus(undefined);
        setPageNumber(1);

        setTimeout(() => {
            fetchProducts();
        }, 0);
    };

    const handleDelete = (product: Product) => {
        Modal.confirm({
            title: 'Delete product',
            content: `Are you sure you want to delete "${product.name}"?`,
            okText: 'Delete',
            okButtonProps: {
                danger: true,
            },
            async onOk() {
                try {
                    const response = await deleteProductApi(product.id);

                    if (!response.success) {
                        message.error(response.message || 'Failed to delete product');
                        return;
                    }

                    message.success('Product deleted successfully');
                    fetchProducts();
                } catch (error: any) {
                    const errorMessage =
                        error.response?.data?.message || 'Failed to delete product';

                    message.error(errorMessage);
                }
            },
        });
    };

    const columns: ColumnsType<Product> = [
        {
            title: 'ID',
            dataIndex: 'id',
            width: 80,
        },
        {
            title: 'Product Name',
            dataIndex: 'name',
            render: (value: string) => <Text strong>{value}</Text>,
        },
        {
            title: 'Category',
            dataIndex: 'category',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            render: (value: number) => formatCurrency(value),
        },
        {
            title: 'Quantity',
            dataIndex: 'quantity',
            render: (value: number) => {
                if (value === 0) {
                    return <Text type="danger">{value}</Text>;
                }

                if (value <= 10) {
                    return <Text type="warning">{value}</Text>;
                }

                return <Text>{value}</Text>;
            },
        },
        {
            title: 'Status',
            dataIndex: 'status',
            render: (value: ProductStatus) => <StatusTag status={value} />,
        },
        {
            title: 'Created Date',
            dataIndex: 'createdAt',
            render: (value: string) => formatDateTime(value),
        },
        {
            title: 'Updated Date',
            dataIndex: 'updatedAt',
            render: (value: string) => formatDateTime(value),
        },
    ];

    if (isAdmin) {
        columns.push({
            title: 'Action',
            key: 'action',
            fixed: 'right',
            width: 160,
            render: (_, record) => (
                <Space>
                    <Button
                        icon={<EditOutlined />}
                        onClick={() => navigate(`/products/edit/${record.id}`)}
                    >
                        Edit
                    </Button>

                    <Button
                        danger
                        icon={<DeleteOutlined />}
                        onClick={() => handleDelete(record)}
                    >
                        Delete
                    </Button>
                </Space>
            ),
        });
    }

    return (
        <div>
            <div className="page-heading">
                <div>
                    <Title level={3}>Products</Title>
                    <Text type="secondary">
                        Manage products, quantity, status, search and filters.
                    </Text>
                </div>

                {isAdmin && (
                    <Button
                        type="primary"
                        icon={<PlusOutlined />}
                        onClick={() => navigate('/products/create')}
                    >
                        Add Product
                    </Button>
                )}
            </div>

            <Card className="page-section filter-card">
                <Space wrap>
                    <Input
                        allowClear
                        placeholder="Search product name"
                        prefix={<SearchOutlined />}
                        value={keyword}
                        onChange={(event) => setKeyword(event.target.value)}
                        onPressEnter={handleSearch}
                        style={{ width: 240 }}
                    />

                    <Select
                        allowClear
                        placeholder="Category"
                        value={category}
                        onChange={setCategory}
                        options={categoryOptions}
                        style={{ width: 180 }}
                    />

                    <Select
                        allowClear
                        placeholder="Status"
                        value={status}
                        onChange={setStatus}
                        options={[
                            { label: 'ACTIVE', value: 'ACTIVE' },
                            { label: 'INACTIVE', value: 'INACTIVE' },
                        ]}
                        style={{ width: 160 }}
                    />

                    <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>
                        Search
                    </Button>

                    <Button icon={<ReloadOutlined />} onClick={handleReset}>
                        Reset
                    </Button>
                </Space>
            </Card>

            <Card className="page-section">
                <Table
                    rowKey="id"
                    columns={columns}
                    dataSource={products}
                    loading={loading}
                    scroll={{ x: 1200 }}
                    pagination={{
                        current: pageNumber,
                        pageSize,
                        total: totalElements,
                        showSizeChanger: true,
                        showTotal: (total) => `Total ${total} products`,
                        onChange: (page, size) => {
                            setPageNumber(page);
                            setPageSize(size);
                        },
                    }}
                />
            </Card>
        </div>
    );
}

export default ProductListPage;