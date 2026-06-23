import {
    Alert,
    Button,
    Card,
    Input, message,
    Select,
    Space,
    Table,
    Typography,
} from 'antd';
import type { ColumnsType } from 'antd/es/table';
import {
    FileSearchOutlined,
    ReloadOutlined,
    SearchOutlined,
} from '@ant-design/icons';
import { useEffect, useMemo, useState } from 'react';
import { getInventoryReportApi } from '../api/inventoryApi';
import StockStatusTag from '../components/StockStatusTag';
import type { InventoryReportItem, StockStatus } from '../types/report';
import { formatDateTime } from '../utils/formatDate';

const { Title, Text } = Typography;

function InventoryReportPage() {
    const [reports, setReports] = useState<InventoryReportItem[]>([]);
    const [category, setCategory] = useState<string | undefined>();
    const [categoryInput, setCategoryInput] = useState('');
    const [stockStatus, setStockStatus] = useState<StockStatus | undefined>();
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const categoryOptions = useMemo(() => {
        const uniqueCategories = Array.from(
            new Set(reports.map((item) => item.category).filter(Boolean))
        );

        return uniqueCategories.map((item) => ({
            label: item,
            value: item,
        }));
    }, [reports]);

    const fetchInventoryReport = async (
        nextCategory = category,
        nextStockStatus = stockStatus
    ) => {
        try {
            setLoading(true);
            setErrorMessage('');

            const response = await getInventoryReportApi({
                category: nextCategory,
                stockStatus: nextStockStatus,
            });

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to load inventory report');
                return;
            }

            setReports(response.data);
        } catch (error: any) {
            const errorText =
                error.response?.data?.message || 'Failed to load inventory report';

            setErrorMessage(errorText);
            message.error(errorText);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        let cancelled = false;

        getInventoryReportApi({})
            .then((response) => {
                if (cancelled) return;

                if (!response.success) {
                    setErrorMessage(response.message || 'Failed to load inventory report');
                    return;
                }

                setReports(response.data);
            })
            .catch((error) => {
                if (cancelled) return;

                const message =
                    error.response?.data?.message || 'Failed to load inventory report';

                setErrorMessage(message);
            })
            .finally(() => {
                if (cancelled) return;

                setLoading(false);
            });

        return () => {
            cancelled = true;
        };
    }, []);

    const handleSearch = () => {
        const nextCategory = categoryInput.trim() || category;
        setCategory(nextCategory);
        fetchInventoryReport(nextCategory, stockStatus);
    };

    const handleReset = () => {
        setCategory(undefined);
        setCategoryInput('');
        setStockStatus(undefined);
        fetchInventoryReport(undefined, undefined);
    };

    const columns: ColumnsType<InventoryReportItem> = [
        {
            title: 'Product ID',
            dataIndex: 'productId',
            width: 110,
        },
        {
            title: 'Product Name',
            dataIndex: 'productName',
            render: (value: string) => <Text strong>{value}</Text>,
        },
        {
            title: 'Category',
            dataIndex: 'category',
        },
        {
            title: 'Current Quantity',
            dataIndex: 'quantity',
            render: (value: number) => {
                if (value === 0) {
                    return <Text type="danger" strong>{value}</Text>;
                }

                if (value <= 10) {
                    return <Text type="warning" strong>{value}</Text>;
                }

                return <Text strong>{value}</Text>;
            },
        },
        {
            title: 'Stock Status',
            dataIndex: 'stockStatus',
            render: (value: StockStatus) => <StockStatusTag status={value} />,
        },
        {
            title: 'Last Updated Date',
            dataIndex: 'lastUpdatedDate',
            render: (value: string) => formatDateTime(value),
        },
    ];

    const totalProducts = reports.length;
    const inStockCount = reports.filter(
        (item) => item.stockStatus === 'IN_STOCK'
    ).length;
    const lowStockCount = reports.filter(
        (item) => item.stockStatus === 'LOW_STOCK'
    ).length;
    const outOfStockCount = reports.filter(
        (item) => item.stockStatus === 'OUT_OF_STOCK'
    ).length;

    return (
        <div>
            <div className="page-heading">
                <div>
                    <Title level={3}>Inventory Report</Title>
                    <Text type="secondary">
                        View current inventory quantity and stock status by product.
                    </Text>
                </div>

                <Button icon={<ReloadOutlined />} onClick={() => fetchInventoryReport()}>
                    Refresh
                </Button>
            </div>

            {errorMessage && (
                <Alert
                    className="page-section"
                    type="error"
                    message="Cannot load inventory report"
                    description={errorMessage}
                    showIcon
                />
            )}

            <div className="report-summary-grid page-section">
                <Card className="report-summary-card">
                    <Text type="secondary">Total Products</Text>
                    <Title level={3}>{totalProducts}</Title>
                </Card>

                <Card className="report-summary-card">
                    <Text type="secondary">In Stock</Text>
                    <Title level={3}>{inStockCount}</Title>
                </Card>

                <Card className="report-summary-card warning-card">
                    <Text type="secondary">Low Stock</Text>
                    <Title level={3}>{lowStockCount}</Title>
                </Card>

                <Card className="report-summary-card danger-card">
                    <Text type="secondary">Out of Stock</Text>
                    <Title level={3}>{outOfStockCount}</Title>
                </Card>
            </div>

            <Card className="page-section filter-card">
                <Space wrap>
                    <Input
                        allowClear
                        placeholder="Enter category"
                        prefix={<SearchOutlined />}
                        value={categoryInput}
                        onChange={(event) => setCategoryInput(event.target.value)}
                        onPressEnter={handleSearch}
                        style={{ width: 220 }}
                    />

                    <Select
                        allowClear
                        showSearch
                        placeholder="Or select category"
                        value={category}
                        onChange={(value) => {
                            setCategory(value);
                            setCategoryInput('');
                        }}
                        options={categoryOptions}
                        style={{ width: 200 }}
                    />

                    <Select
                        allowClear
                        placeholder="Stock status"
                        value={stockStatus}
                        onChange={setStockStatus}
                        options={[
                            { label: 'IN STOCK', value: 'IN_STOCK' },
                            { label: 'LOW STOCK', value: 'LOW_STOCK' },
                            { label: 'OUT OF STOCK', value: 'OUT_OF_STOCK' },
                        ]}
                        style={{ width: 180 }}
                    />

                    <Button
                        type="primary"
                        icon={<FileSearchOutlined />}
                        onClick={handleSearch}
                    >
                        View Report
                    </Button>

                    <Button icon={<ReloadOutlined />} onClick={handleReset}>
                        Reset
                    </Button>
                </Space>
            </Card>

            <Card className="page-section">
                <Table
                    rowKey="productId"
                    columns={columns}
                    dataSource={reports}
                    loading={loading}
                    scroll={{ x: 900 }}
                    pagination={{
                        pageSize: 10,
                        showSizeChanger: true,
                        showTotal: (total) => `Total ${total} products`,
                    }}
                />
            </Card>
        </div>
    );
}

export default InventoryReportPage;