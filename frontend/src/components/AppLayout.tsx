import {
    AppstoreOutlined,
    DashboardOutlined,
    FileSearchOutlined,
    ImportOutlined,
    LogoutOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    ProductOutlined,
    ExportOutlined,
} from '@ant-design/icons';
import { Avatar, Button, Layout, Menu, Space, Tag, Typography } from 'antd';
import { useState } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { clearAuthUser, getAuthUser } from '../utils/authStorage';

const { Header, Sider, Content } = Layout;
const { Text, Title } = Typography;

function AppLayout() {
    const [collapsed, setCollapsed] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const authUser = getAuthUser();

    const handleLogout = () => {
        clearAuthUser();
        navigate('/login');
    };

    const selectedKey = (() => {
        const path = location.pathname;

        if (path.startsWith('/products')) return '/products';
        if (path.startsWith('/stock-in')) return '/stock-in/create';
        if (path.startsWith('/stock-out')) return '/stock-out/create';
        if (path.startsWith('/inventory')) return '/inventory/report';

        return '/dashboard';
    })();

    return (
        <Layout className="app-shell">
            <Sider
                trigger={null}
                collapsible
                collapsed={collapsed}
                width={250}
                className="app-sider"
            >
                <div className="brand">
                    <div className="brand-logo">S</div>
                    {!collapsed && (
                        <div>
                            <div className="brand-title">StockFlow</div>
                            <div className="brand-subtitle">Inventory System</div>
                        </div>
                    )}
                </div>

                <Menu
                    theme="dark"
                    mode="inline"
                    selectedKeys={[selectedKey]}
                    onClick={({ key }) => navigate(key)}
                    items={[
                        {
                            key: '/dashboard',
                            icon: <DashboardOutlined />,
                            label: 'Dashboard',
                        },
                        {
                            key: '/products',
                            icon: <ProductOutlined />,
                            label: 'Products',
                        },
                        {
                            key: '/stock-in/create',
                            icon: <ImportOutlined />,
                            label: 'Stock In',
                        },
                        {
                            key: '/stock-out/create',
                            icon: <ExportOutlined />,
                            label: 'Stock Out',
                        },
                        {
                            key: '/stock-in/history',
                            icon: <AppstoreOutlined />,
                            label: 'Stock-In History',
                        },
                        {
                            key: '/stock-out/history',
                            icon: <AppstoreOutlined />,
                            label: 'Stock-Out History',
                        },
                        {
                            key: '/inventory/report',
                            icon: <FileSearchOutlined />,
                            label: 'Inventory Report',
                        },
                    ]}
                />
            </Sider>

            <Layout>
                <Header className="app-header">
                    <Space>
                        <Button
                            type="text"
                            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                            onClick={() => setCollapsed(!collapsed)}
                        />
                        <Title level={4} className="page-title">
                            StockFlow
                        </Title>
                    </Space>

                    <Space size="middle">
                        <Avatar className="user-avatar">
                            {authUser?.fullName?.charAt(0) ?? 'U'}
                        </Avatar>

                        <div className="user-info">
                            <Text strong>{authUser?.fullName ?? 'Unknown User'}</Text>
                            <Tag color={authUser?.role === 'ADMIN' ? 'blue' : 'green'}>
                                {authUser?.role ?? 'GUEST'}
                            </Tag>
                        </div>

                        <Button icon={<LogoutOutlined />} onClick={handleLogout}>
                            Logout
                        </Button>
                    </Space>
                </Header>

                <Content className="app-content">
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}

export default AppLayout;