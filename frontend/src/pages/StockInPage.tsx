import {
    Alert,
    Button,
    Card,
    Form,
    InputNumber,
    Select,
    Skeleton,
    Space,
    Typography,
    message,
} from 'antd';
import {
    ImportOutlined,
    ReloadOutlined,
} from '@ant-design/icons';
import { useEffect, useMemo, useState } from 'react';
import { getProductsApi } from '../api/productApi';
import { createStockInApi } from '../api/stockInApi';
import type { Product } from '../types/product';
import type { StockInRequest } from '../types/stock';
import { formatCurrency } from '../utils/formatCurrency';

const { Title, Text } = Typography;

interface StockInFormValues {
    productId: number;
    quantity: number;
    note?: string;
}

function StockInPage() {
    const [form] = Form.useForm<StockInFormValues>();

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
    };

    const handleSubmit = async (values: StockInFormValues) => {
        try {
            setSubmitting(true);
            setErrorMessage('');

            const request: StockInRequest = {
                productId: values.productId,
                quantity: values.quantity,
                note: values.note?.trim() || undefined,
            };

            const response = await createStockInApi(request);

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to create stock-in');
                return;
            }

            message.success('Stock-in transaction created successfully');

            form.resetFields();
            setSelectedProduct(null);
            fetchProducts();
        } catch (error: any) {
            const message =
                error.response?.data?.message || 'Failed to create stock-in';

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
                    <Title level={3}>Stock In</Title>
                    <Text type="secondary">
                        Create a stock-in transaction to increase product inventory quantity.
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
                                id="stock-in-product-select"
                                data-testid="stock-in-product-select"
                                className="stock-in-product-select"
                                popupClassName="stock-in-product-dropdown"
                                showSearch
                                placeholder="Select active product"
                                options={productOptions}
                                onChange={handleProductChange}
                                optionFilterProp="label"
                                filterOption={(input, option) =>
                                    String(option?.label ?? "")
                                        .toLowerCase()
                                        .includes(input.toLowerCase())
                                }
                            />
                        </Form.Item>

                        <Form.Item
                            label="Quantity"
                            name="quantity"
                            rules={[
                                { required: true, message: 'Quantity is required' },
                                {
                                    type: 'number',
                                    min: 1,
                                    message: 'Quantity must be greater than 0',
                                },
                            ]}
                        >
                            <InputNumber
                                id="stock-in-quantity-input"
                                min={1}
                                style={{ width: '100%' }}
                                placeholder="Example: 5"
                            />
                        </Form.Item>

                        <Form.Item label="Note" name="note">
              <textarea
                  id="stock-in-note-input"
                  className="textarea-input"
                  placeholder="Example: Import from supplier"
                  rows={4}
              />
                        </Form.Item>

                        <Space>
                            <Button
                                id="stock-in-submit-button"
                                type="primary"
                                htmlType="submit"
                                icon={<ImportOutlined />}
                                loading={submitting}
                            >
                                Create Stock-In
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
                                <Title level={2} className="quantity-preview">
                                    {selectedProduct.quantity}
                                </Title>
                            </div>

                            <Alert
                                type="info"
                                showIcon
                                message="After stock-in"
                                description="Product quantity will increase by the stock-in quantity."
                            />
                        </Space>
                    )}
                </Card>
            </div>
        </div>
    );
}

export default StockInPage;