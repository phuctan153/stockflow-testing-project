import { Tag } from 'antd';
import type { StockStatus } from '../types/report';

interface StockStatusTagProps {
    status: StockStatus;
}

function StockStatusTag({ status }: StockStatusTagProps) {
    if (status === 'IN_STOCK') {
        return <Tag color="green">IN STOCK</Tag>;
    }

    if (status === 'LOW_STOCK') {
        return <Tag color="orange">LOW STOCK</Tag>;
    }

    return <Tag color="red">OUT OF STOCK</Tag>;
}

export default StockStatusTag;