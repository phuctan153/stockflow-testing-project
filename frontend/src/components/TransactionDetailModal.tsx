import { Descriptions, Modal, Skeleton, Tag, Typography } from 'antd';
import type { StockTransaction } from '../types/stock';
import { formatDateTime } from '../utils/formatDate';

const { Text } = Typography;

interface TransactionDetailModalProps {
    open: boolean;
    loading: boolean;
    title: string;
    type: 'STOCK_IN' | 'STOCK_OUT';
    transaction: StockTransaction | null;
    onClose: () => void;
}

function TransactionDetailModal({
                                    open,
                                    loading,
                                    title,
                                    type,
                                    transaction,
                                    onClose,
                                }: TransactionDetailModalProps) {
    return (
        <Modal
            open={open}
            title={title}
            onCancel={onClose}
            footer={null}
            width={720}
            destroyOnClose
        >
            {loading ? (
                <Skeleton active paragraph={{ rows: 6 }} />
            ) : !transaction ? (
                <Text type="secondary">No transaction detail found.</Text>
            ) : (
                <Descriptions bordered column={1}>
                    <Descriptions.Item label="Transaction ID">
                        {transaction.id}
                    </Descriptions.Item>

                    <Descriptions.Item label="Type">
                        {type === 'STOCK_IN' ? (
                            <Tag color="blue">STOCK IN</Tag>
                        ) : (
                            <Tag color="orange">STOCK OUT</Tag>
                        )}
                    </Descriptions.Item>

                    <Descriptions.Item label="Product ID">
                        {transaction.productId}
                    </Descriptions.Item>

                    <Descriptions.Item label="Product Name">
                        <Text strong>{transaction.productName}</Text>
                    </Descriptions.Item>

                    <Descriptions.Item label="Quantity">
                        <Text strong>{transaction.quantity}</Text>
                    </Descriptions.Item>

                    <Descriptions.Item label={type === 'STOCK_IN' ? 'Note' : 'Reason'}>
                        {type === 'STOCK_IN'
                            ? transaction.note || '-'
                            : transaction.reason || '-'}
                    </Descriptions.Item>

                    <Descriptions.Item label="Created By">
                        User ID: {transaction.createdBy}
                    </Descriptions.Item>

                    <Descriptions.Item label="Created At">
                        {formatDateTime(transaction.createdAt)}
                    </Descriptions.Item>
                </Descriptions>
            )}
        </Modal>
    );
}

export default TransactionDetailModal;