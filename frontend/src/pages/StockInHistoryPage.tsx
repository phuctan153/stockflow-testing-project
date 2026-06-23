import {
    Button,
    Card,
    DatePicker,
    Input,
    Space,
    Table,
    Tag,
    Typography,
    message,
} from 'antd';
import type { ColumnsType } from 'antd/es/table';
import {
    EyeOutlined,
    ReloadOutlined,
    SearchOutlined,
} from '@ant-design/icons';
import { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import {
    getStockInDetailApi,
    getStockInHistoryApi,
} from '../api/stockInApi';
import TransactionDetailModal from '../components/TransactionDetailModal';
import type { StockTransaction } from '../types/stock';
import { formatDateTime } from '../utils/formatDate';

const { Title, Text } = Typography;
const { RangePicker } = DatePicker;

function StockInHistoryPage() {
    const [transactions, setTransactions] = useState<StockTransaction[]>([]);
    const [productName, setProductName] = useState('');
    const [dateRange, setDateRange] = useState<[dayjs.Dayjs, dayjs.Dayjs] | null>(
        null
    );

    const [loading, setLoading] = useState(false);
    const [pageNumber, setPageNumber] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [totalElements, setTotalElements] = useState(0);

    const [detailOpen, setDetailOpen] = useState(false);
    const [detailLoading, setDetailLoading] = useState(false);
    const [selectedTransaction, setSelectedTransaction] =
        useState<StockTransaction | null>(null);

    const fetchTransactions = async () => {
        try {
            setLoading(true);

            const response = await getStockInHistoryApi({
                productName: productName || undefined,
                fromDate: dateRange?.[0]?.format('YYYY-MM-DD'),
                toDate: dateRange?.[1]?.format('YYYY-MM-DD'),
                page: pageNumber - 1,
                size: pageSize,
                sortBy: 'createdAt',
                sortDirection: 'desc',
            });

            if (!response.success) {
                message.error(response.message || 'Failed to load stock-in history');
                return;
            }

            setTransactions(response.data.content);
            setTotalElements(response.data.totalElements);
        } catch (error: any) {
            const errorMessage =
                error.response?.data?.message || 'Failed to load stock-in history';

            message.error(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTransactions();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pageNumber, pageSize]);

    const handleSearch = () => {
        setPageNumber(1);
        fetchTransactions();
    };

    const handleReset = () => {
        setProductName('');
        setDateRange(null);
        setPageNumber(1);

        setTimeout(() => {
            fetchTransactions();
        }, 0);
    };

    const handleViewDetail = async (id: number) => {
        try {
            setDetailOpen(true);
            setDetailLoading(true);
            setSelectedTransaction(null);

            const response = await getStockInDetailApi(id);

            if (!response.success) {
                message.error(response.message || 'Failed to load stock-in detail');
                return;
            }

            setSelectedTransaction(response.data);
        } catch (error: any) {
            const errorMessage =
                error.response?.data?.message || 'Failed to load stock-in detail';

            message.error(errorMessage);
        } finally {
            setDetailLoading(false);
        }
    };

    const columns: ColumnsType<StockTransaction> = [
        {
            title: 'ID',
            dataIndex: 'id',
            width: 80,
        },
        {
            title: 'Type',
            key: 'type',
            render: () => <Tag color="blue">STOCK IN</Tag>,
        },
        {
            title: 'Product Name',
            dataIndex: 'productName',
            render: (value: string) => <Text strong>{value}</Text>,
        },
        {
            title: 'Quantity',
            dataIndex: 'quantity',
            render: (value: number) => <Text strong>+{value}</Text>,
        },
        {
            title: 'Note',
            dataIndex: 'note',
            render: (value?: string | null) => value || '-',
        },
        {
            title: 'Created By',
            dataIndex: 'createdBy',
            render: (value: number) => `User ID: ${value}`,
        },
        {
            title: 'Created At',
            dataIndex: 'createdAt',
            render: (value: string) => formatDateTime(value),
        },
        {
            title: 'Action',
            key: 'action',
            width: 120,
            render: (_, record) => (
                <Button
                    icon={<EyeOutlined />}
                    onClick={() => handleViewDetail(record.id)}
                >
                    Detail
                </Button>
            ),
        },
    ];

    return (
        <div>
            <div className="page-heading">
                <div>
                    <Title level={3}>Stock-In History</Title>
                    <Text type="secondary">
                        View stock-in transaction history and filter by product/date.
                    </Text>
                </div>

                <Button icon={<ReloadOutlined />} onClick={fetchTransactions}>
                    Refresh
                </Button>
            </div>

            <Card className="page-section filter-card">
                <Space wrap>
                    <Input
                        allowClear
                        placeholder="Search product name"
                        prefix={<SearchOutlined />}
                        value={productName}
                        onChange={(event) => setProductName(event.target.value)}
                        onPressEnter={handleSearch}
                        style={{ width: 240 }}
                    />

                    <RangePicker
                        value={dateRange}
                        onChange={(value) =>
                            setDateRange(value as [dayjs.Dayjs, dayjs.Dayjs] | null)
                        }
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
                    dataSource={transactions}
                    loading={loading}
                    scroll={{ x: 1000 }}
                    pagination={{
                        current: pageNumber,
                        pageSize,
                        total: totalElements,
                        showSizeChanger: true,
                        showTotal: (total) => `Total ${total} transactions`,
                        onChange: (page, size) => {
                            setPageNumber(page);
                            setPageSize(size);
                        },
                    }}
                />
            </Card>

            <TransactionDetailModal
                open={detailOpen}
                loading={detailLoading}
                title="Stock-In Detail"
                type="STOCK_IN"
                transaction={selectedTransaction}
                onClose={() => setDetailOpen(false)}
            />
        </div>
    );
}

export default StockInHistoryPage;