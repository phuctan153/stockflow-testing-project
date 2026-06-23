import {
    Alert,
    Button,
    Card,
    Form,
    Input,
    InputNumber,
    Select,
    Skeleton,
    Space,
    Typography,
    message,
} from 'antd';
import {
    ArrowLeftOutlined,
    SaveOutlined,
} from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
    createProductApi,
    getProductDetailApi,
    updateProductApi,
} from '../api/productApi';
import type {
    CreateProductRequest,
    Product,
    ProductStatus,
    UpdateProductRequest,
} from '../types/product';

const { Title, Text } = Typography;

interface ProductFormValues {
    name: string;
    category: string;
    price: number;
    quantity?: number;
    status: ProductStatus;
}

function ProductFormPage() {
    const [form] = Form.useForm<ProductFormValues>();
    const navigate = useNavigate();
    const { id } = useParams();

    const isEditMode = Boolean(id);

    const [loading, setLoading] = useState(false);
    const [loadingDetail, setLoadingDetail] = useState(isEditMode);
    const [errorMessage, setErrorMessage] = useState('');
    const [productDetail, setProductDetail] = useState<Product | null>(null);

    useEffect(() => {
        if (!id) {
            form.setFieldsValue({
                status: 'ACTIVE',
                quantity: 0,
            });
            return;
        }

        let cancelled = false;

        getProductDetailApi(Number(id))
            .then((response) => {
                if (cancelled) return;

                if (!response.success) {
                    setErrorMessage(response.message || 'Failed to load product detail');
                    return;
                }

                const product = response.data;
                setProductDetail(product);

                form.setFieldsValue({
                    name: product.name,
                    category: product.category,
                    price: product.price,
                    status: product.status,
                });
            })
            .catch((error) => {
                if (cancelled) return;

                const message =
                    error.response?.data?.message || 'Failed to load product detail';

                setErrorMessage(message);
            })
            .finally(() => {
                if (cancelled) return;

                setLoadingDetail(false);
            });

        return () => {
            cancelled = true;
        };
    }, [id, form]);

    const handleSubmit = async (values: ProductFormValues) => {
        try {
            setLoading(true);
            setErrorMessage('');

            if (isEditMode && id) {
                const request: UpdateProductRequest = {
                    name: values.name.trim(),
                    category: values.category.trim(),
                    price: values.price,
                    status: values.status,
                };

                const response = await updateProductApi(Number(id), request);

                if (!response.success) {
                    setErrorMessage(response.message || 'Failed to update product');
                    return;
                }

                message.success('Product updated successfully');
                navigate('/products');
                return;
            }

            const request: CreateProductRequest = {
                name: values.name.trim(),
                category: values.category.trim(),
                price: values.price,
                quantity: values.quantity ?? 0,
                status: values.status,
            };

            const response = await createProductApi(request);

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to create product');
                return;
            }

            message.success('Product created successfully');
            navigate('/products');
        } catch (error: any) {
            const message =
                error.response?.data?.message ||
                (isEditMode ? 'Failed to update product' : 'Failed to create product');

            setErrorMessage(message);
        } finally {
            setLoading(false);
        }
    };

    if (loadingDetail) {
        return (
            <Card>
                <Skeleton active paragraph={{ rows: 8 }} />
            </Card>
        );
    }

    return (
        <div>
            <div className="page-heading">
                <div>
                    <Title level={3}>
                        {isEditMode ? 'Edit Product' : 'Create Product'}
                    </Title>
                    <Text type="secondary">
                        {isEditMode
                            ? 'Update product information. Quantity cannot be edited directly.'
                            : 'Create a new product with initial inventory quantity.'}
                    </Text>
                </div>

                <Button
                    icon={<ArrowLeftOutlined />}
                    onClick={() => navigate('/products')}
                >
                    Back to Products
                </Button>
            </div>

            {errorMessage && (
                <Alert
                    className="page-section"
                    type="error"
                    message="Action failed"
                    description={errorMessage}
                    showIcon
                />
            )}

            <Card className="page-section form-card">
                {isEditMode && productDetail && (
                    <Alert
                        className="form-note"
                        type="info"
                        showIcon
                        message={`Current quantity: ${productDetail.quantity}`}
                        description="Product quantity cannot be edited directly. Use Stock-In or Stock-Out transactions to change inventory quantity."
                    />
                )}

                <Form
                    form={form}
                    layout="vertical"
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Product Name"
                        name="name"
                        rules={[
                            { required: true, message: 'Product name is required' },
                            { max: 100, message: 'Product name must not exceed 100 characters' },
                        ]}
                    >
                        <Input placeholder="Example: Laptop Dell Inspiron" />
                    </Form.Item>

                    <Form.Item
                        label="Category"
                        name="category"
                        rules={[
                            { required: true, message: 'Category is required' },
                            { max: 50, message: 'Category must not exceed 50 characters' },
                        ]}
                    >
                        <Input placeholder="Example: Laptop" />
                    </Form.Item>

                    <Form.Item
                        label="Price"
                        name="price"
                        rules={[
                            { required: true, message: 'Price is required' },
                            {
                                type: 'number',
                                min: 1,
                                message: 'Price must be greater than 0',
                            },
                        ]}
                    >
                        <InputNumber
                            min={1}
                            style={{ width: '100%' }}
                            placeholder="Example: 15000000"
                            addonAfter="VND"
                        />
                    </Form.Item>

                    {!isEditMode && (
                        <Form.Item
                            label="Initial Quantity"
                            name="quantity"
                            rules={[
                                { required: true, message: 'Initial quantity is required' },
                                {
                                    type: 'number',
                                    min: 0,
                                    message: 'Initial quantity must be greater than or equal to 0',
                                },
                            ]}
                        >
                            <InputNumber
                                min={0}
                                style={{ width: '100%' }}
                                placeholder="Example: 10"
                            />
                        </Form.Item>
                    )}

                    <Form.Item
                        label="Status"
                        name="status"
                        rules={[{ required: true, message: 'Status is required' }]}
                    >
                        <Select
                            options={[
                                { label: 'ACTIVE', value: 'ACTIVE' },
                                { label: 'INACTIVE', value: 'INACTIVE' },
                            ]}
                        />
                    </Form.Item>

                    <Space>
                        <Button
                            type="primary"
                            htmlType="submit"
                            icon={<SaveOutlined />}
                            loading={loading}
                        >
                            {isEditMode ? 'Update Product' : 'Create Product'}
                        </Button>

                        <Button onClick={() => navigate('/products')}>
                            Cancel
                        </Button>
                    </Space>
                </Form>
            </Card>
        </div>
    );
}

export default ProductFormPage;