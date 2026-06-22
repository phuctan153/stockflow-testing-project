import { Card, Col, Row, Typography } from 'antd';

const { Title, Text } = Typography;

function DashboardPage() {
    return (
        <div>
            <Title level={3}>Dashboard</Title>
            <Text type="secondary">
                Overview of products, stock quantity, low-stock and out-of-stock items.
            </Text>

            <Row gutter={[16, 16]} className="page-section">
                <Col xs={24} sm={12} lg={8}>
                    <Card>
                        <Text type="secondary">Total Products</Text>
                        <Title level={2}>--</Title>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card>
                        <Text type="secondary">Active Products</Text>
                        <Title level={2}>--</Title>
                    </Card>
                </Col>

                <Col xs={24} sm={12} lg={8}>
                    <Card>
                        <Text type="secondary">Total Stock Quantity</Text>
                        <Title level={2}>--</Title>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}

export default DashboardPage;