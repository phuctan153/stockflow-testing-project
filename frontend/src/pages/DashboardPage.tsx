import {
    Alert,
    Card,
    Col,
    Row,
    Skeleton,
    Space,
    Statistic,
    Typography,
} from 'antd';
import {
    AppstoreOutlined,
    CheckCircleOutlined,
    CloseCircleOutlined,
    DatabaseOutlined,
    ExclamationCircleOutlined,
    InboxOutlined,
    ReloadOutlined,
} from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { Button } from 'antd';
import { getDashboardSummaryApi } from '../api/dashboardApi';
import type { DashboardSummary } from '../types/dashboard';

const { Title, Text } = Typography;

function DashboardPage() {
    const [summary, setSummary] = useState<DashboardSummary | null>(null);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');

    const fetchDashboardSummary = async () => {
        try {
            setLoading(true);
            setErrorMessage('');

            const response = await getDashboardSummaryApi();

            if (!response.success) {
                setErrorMessage(response.message || 'Failed to load dashboard summary');
                return;
            }

            setSummary(response.data);
        } catch (error: any) {
            const message =
                error.response?.data?.message || 'Failed to load dashboard summary';

            setErrorMessage(message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        let cancelled = false;

        getDashboardSummaryApi()
            .then((response) => {
                if (cancelled) return;

                if (!response.success) {
                    setErrorMessage(response.message || 'Failed to load dashboard summary');
                    return;
                }

                setSummary(response.data);
            })
            .catch((error) => {
                if (cancelled) return;

                const message =
                    error.response?.data?.message || 'Failed to load dashboard summary';

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

    if (loading) {
        return (
            <div>
                <div className="page-heading">
                    <div>
                        <Title level={3}>Dashboard</Title>
                        <Text type="secondary">
                            Overview of your inventory status and product statistics.
                        </Text>
                    </div>
                </div>

                <Row gutter={[16, 16]} className="page-section">
                    {Array.from({ length: 6 }).map((_, index) => (
                        <Col xs={24} sm={12} lg={8} key={index}>
                            <Card className="stat-card">
                                <Skeleton active paragraph={{ rows: 1 }} />
                            </Card>
                        </Col>
                    ))}
                </Row>
            </div>
        );
    }

    return (
        <div>
            <div className="page-heading">
                <div>
                    <Title level={3}>Dashboard</Title>
                    <Text type="secondary">
                        Overview of your inventory status and product statistics.
                    </Text>
                </div>

                <Button icon={<ReloadOutlined />} onClick={fetchDashboardSummary}>
                    Refresh
                </Button>
            </div>

            {errorMessage && (
                <Alert
                    className="page-section"
                    type="error"
                    message="Cannot load dashboard"
                    description={errorMessage}
                    showIcon
                />
            )}

            <Row gutter={[16, 16]} className="page-section">
                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card">
                        <Statistic
                            title="Total Products"
                            value={summary?.totalProducts ?? 0}
                            prefix={<AppstoreOutlined />}
                        />
                        <Text type="secondary">All products in the system</Text>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card">
                        <Statistic
                            title="Active Products"
                            value={summary?.activeProducts ?? 0}
                            prefix={<CheckCircleOutlined />}
                        />
                        <Text type="secondary">Products available for transactions</Text>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card">
                        <Statistic
                            title="Inactive Products"
                            value={summary?.inactiveProducts ?? 0}
                            prefix={<CloseCircleOutlined />}
                        />
                        <Text type="secondary">Products blocked from new transactions</Text>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card">
                        <Statistic
                            title="Total Stock Quantity"
                            value={summary?.totalStockQuantity ?? 0}
                            prefix={<DatabaseOutlined />}
                        />
                        <Text type="secondary">Current total inventory quantity</Text>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card warning-card">
                        <Statistic
                            title="Low-Stock Products"
                            value={summary?.lowStockProducts ?? 0}
                            prefix={<ExclamationCircleOutlined />}
                        />
                        <Text type="secondary">Products with quantity from 1 to 10</Text>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card className="stat-card danger-card">
                        <Statistic
                            title="Out-of-Stock Products"
                            value={summary?.outOfStockProducts ?? 0}
                            prefix={<InboxOutlined />}
                        />
                        <Text type="secondary">Products with quantity equal to 0</Text>
                    </Card>
                </Col>
            </Row>

            <Card className="page-section">
                <Space direction="vertical" size={4}>
                    <Title level={5}>Stock status rules</Title>
                    <Text type="secondary">
                        Low stock: quantity from 1 to 10. Out of stock: quantity equals 0.
                    </Text>
                </Space>
            </Card>
        </div>
    );
}

export default DashboardPage;