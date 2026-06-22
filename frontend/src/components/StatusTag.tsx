import { Tag } from 'antd';
import type { ProductStatus } from '../types/product';

interface StatusTagProps {
    status: ProductStatus;
}

function StatusTag({ status }: StatusTagProps) {
    if (status === 'ACTIVE') {
        return <Tag color="green">ACTIVE</Tag>;
    }

    return <Tag color="red">INACTIVE</Tag>;
}

export default StatusTag;