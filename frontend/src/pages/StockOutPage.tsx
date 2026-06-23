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
    ExportOutlined,
    ReloadOutlined,
} from '@ant-design/icons';
import { useEffect, useMemo, useState } from 'react';
import { getProductsApi } from '../api/productApi';
import { createStockOutApi } from '../api/stockOutApi';
import type { Product } from '../types/product';
import type { StockOutRequest } from '../types/stock';
import { formatCurrency } from '../utils/formatCurrency';

const { Title, Text } = Typography;
const { TextArea } = Input;

interface StockOutFormValues {
    productId: number;
    quantity: number;
    reason?: string;
}

function StockOutPage() {
    const [form] = Form.useForm<StockOutFormValues>();

    const [products, setProducts] = useState<Product[]>([]);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [loadingProducts, setLoadingProducts] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const activeProducts = useMemo(() => {
        return products.filter((product) => product.status === 'ACTIVE');
    }, [products]);

    const productOptions = useMemo(() => {
        return activeProducts.map((product) => ({
            label: `${product.name} - Qty: ${product.quantity}`,
            value: product.id,
        }));
    }, [activeProducts]);

    const fetchProducts = async () => {
        try {
            setLoadingProducts(true);
            setErrorMessage('');

            const response = await getProductsApi({
                page: 0,
                size: 100,
                sortBy: 'name',
                sortDirection: 'asc',
                status: 'ACTIVE',
            });

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to load products');
                return;
            }

            setProducts(response.data.content);
        } catch (error: any) {
            const message =
                error.response?.data?.message || 'Failed to load products';

            setErrorMessage(message);
        } finally {
            setLoadingProducts(false);
        }
    };

    useEffect(() => {
        let cancelled = false;

        getProductsApi({
            page: 0,
            size: 100,
            sortBy: 'name',
            sortDirection: 'asc',
            status: 'ACTIVE',
        })
            .then((response) => {
                if (cancelled) return;

                if (!response.success) {
                    setErrorMessage(response.message || 'Failed to load products');
                    return;
                }

                setProducts(response.data.content);
            })
            .catch((error) => {
                if (cancelled) return;

                const message =
                    error.response?.data?.message || 'Failed to load products';

                setErrorMessage(message);
            })
            .finally(() => {
                if (cancelled) return;
                setLoadingProducts(false);
            });

        return () => {
            cancelled = true;
        };
    }, []);

    const handleProductChange = (productId: number) => {
        const product = products.find((item) => item.id === productId) ?? null;
        setSelectedProduct(product);

        form.setFieldValue('quantity', undefined);
        setErrorMessage('');
    };

    const handleSubmit = async (values: StockOutFormValues) => {
        try {
            setSubmitting(true);
            setErrorMessage('');

            const request: StockOutRequest = {
                productId: values.productId,
                quantity: values.quantity,
                reason: values.reason?.trim() || undefined,
            };

            const response = await createStockOutApi(request);

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to create stock-out');
                return;
            }

            message.success('Stock-out transaction created successfully');

            form.resetFields();
            setSelectedProduct(null);
            fetchProducts();
        } catch (error: any) {
            const message =
                error.response?.data?.message || 'Failed to create stock-out';

            setErrorMessage(message);
        } finally {
            setSubmitting(false);
        }
    };

    if (loadingProducts) {
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
                    <Title level={3}>Stock Out</Title>
                    <Text type="secondary">
                        Create a stock-out transaction to decrease product inventory quantity.
                    </Text>
                </div>

                <Button icon={<ReloadOutlined />} onClick={fetchProducts}>
                    Refresh Products
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

            <div className="stock-form-grid page-section">
                <Card className="form-card">
                    <Form
                        form={form}
                        layout="vertical"
                        onFinish={handleSubmit}
                        autoComplete="off"
                    >
                        <Form.Item
                            label="Product"
                            name="productId"
                            rules={[{ required: true, message: 'Product is required' }]}
                        >
                            <Select
                                showSearch
                                placeholder="Select active product"
                                options={productOptions}
                                onChange={handleProductChange}
                                optionFilterProp="label"
                            />
                        </Form.Item>

                        <Form.Item
                            label="Quantity"
                            name="quantity"
                            validateTrigger={['onChange', 'onBlur']}
                            rules={[
                                { required: true, message: 'Quantity is required' },
                                {
                                    type: 'number',
                                    min: 1,
                                    message: 'Quantity must be greater than 0',
                                },
                                {
                                    validator: (_, value) => {
                                        if (!value || !selectedProduct) {
                                            return Promise.resolve();
                                        }

                                        if (value > selectedProduct.quantity) {
                                            return Promise.reject(
                                                new Error(
                                                    'Stock-out quantity cannot exceed current inventory quantity'
                                                )
                                            );
                                        }

                                        return Promise.resolve();
                                    },
                                },
                            ]}
                        >
                            <InputNumber
                                min={1}
                                style={{ width: '100%' }}
                                placeholder="Example: 3"
                                disabled={!selectedProduct}
                            />
                        </Form.Item>

                        <Form.Item label="Reason" name="reason">
                            <TextArea
                                placeholder="Example: Sold to customer"
                                rows={4}
                            />
                        </Form.Item>

                        <Space>
                            <Button
                                type="primary"
                                htmlType="submit"
                                icon={<ExportOutlined />}
                                loading={submitting}
                            >
                                Create Stock-Out
                            </Button>

                            <Button
                                onClick={() => {
                                    form.resetFields();
                                    setSelectedProduct(null);
                                    setErrorMessage('');
                                }}
                            >
                                Reset
                            </Button>
                        </Space>
                    </Form>
                </Card>

                <Card className="stock-info-card">
                    <Title level={5}>Selected Product</Title>

                    {!selectedProduct ? (
                        <Text type="secondary">
                            Select a product to see current inventory information.
                        </Text>
                    ) : (
                        <Space direction="vertical" size={10} style={{ width: '100%' }}>
                            <div className="info-row">
                                <Text type="secondary">Product Name</Text>
                                <Text strong>{selectedProduct.name}</Text>
                            </div>

                            <div className="info-row">
                                <Text type="secondary">Category</Text>
                                <Text>{selectedProduct.category}</Text>
                            </div>

                            <div className="info-row">
                                <Text type="secondary">Price</Text>
                                <Text>{formatCurrency(selectedProduct.price)}</Text>
                            </div>

                            <div className="info-row">
                                <Text type="secondary">Current Quantity</Text>
                                <Title
                                    level={2}
                                    className={
                                        selectedProduct.quantity === 0
                                            ? 'quantity-preview danger-text'
                                            : 'quantity-preview'
                                    }
                                >
                                    {selectedProduct.quantity}
                                </Title>
                            </div>

                            {selectedProduct.quantity === 0 ? (
                                <Alert
                                    type="warning"
                                    showIcon
                                    message="Out of stock"
                                    description="This product has no inventory quantity available for stock-out."
                                />
                            ) : (
                                <Alert
                                    type="info"
                                    showIcon
                                    message="After stock-out"
                                    description="Product quantity will decrease by the stock-out quantity."
                                />
                            )}
                        </Space>
                    )}
                </Card>
            </div>
        </div>
    );
}

export default StockOutPage;