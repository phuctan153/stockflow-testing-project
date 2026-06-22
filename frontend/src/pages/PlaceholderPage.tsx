import { Card, Typography } from 'antd';

const { Title, Text } = Typography;

interface PlaceholderPageProps {
    title: string;
    description: string;
}

function PlaceholderPage({ title, description }: PlaceholderPageProps) {
    return (
        <Card>
            <Title level={3}>{title}</Title>
            <Text type="secondary">{description}</Text>
        </Card>
    );
}

export default PlaceholderPage;