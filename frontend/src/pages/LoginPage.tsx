import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Card, Form, Input, Typography, message } from 'antd';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginApi } from '../api/authApi';
import { saveAuthUser } from '../utils/authStorage';
import type { LoginRequest } from '../types/auth';

const { Title, Text } = Typography;

function LoginPage() {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (values: LoginRequest) => {
        try {
            setLoading(true);

            const response = await loginApi(values);

            if (!response.success) {
                message.error(response.message || 'Invalid username or password');
                return;
            }

            saveAuthUser(response.data);
            message.success('Login successful');
            navigate('/dashboard');
        } catch (error: any) {
            const errorMessage =
                error.response?.data?.message || 'Invalid username or password';

            message.error(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">
            <Card className="login-card">
                <div className="login-header">
                    <div className="login-logo">S</div>
                    <Title level={2} className="login-title">
                        StockFlow
                    </Title>
                    <Text type="secondary">Mini Inventory Management System</Text>
                </div>

                <Form layout="vertical" onFinish={handleLogin} autoComplete="off">
                    <Form.Item
                        label="Username"
                        name="username"
                        rules={[{ required: true, message: 'Username is required' }]}
                    >
                        <Input
                            size="large"
                            prefix={<UserOutlined />}
                            placeholder="Enter username"
                        />
                    </Form.Item>

                    <Form.Item
                        label="Password"
                        name="password"
                        rules={[{ required: true, message: 'Password is required' }]}
                    >
                        <Input.Password
                            size="large"
                            prefix={<LockOutlined />}
                            placeholder="Enter password"
                        />
                    </Form.Item>

                    <Button
                        type="primary"
                        htmlType="submit"
                        size="large"
                        block
                        loading={loading}
                    >
                        Login
                    </Button>
                </Form>

                <div className="demo-account-box">
                    <Text type="secondary">Demo accounts</Text>
                    <div>
                        <Text code>admin / 123456</Text>
                    </div>
                    <div>
                        <Text code>staff / 123456</Text>
                    </div>
                </div>
            </Card>
        </div>
    );
}

export default LoginPage;