# StockFlow Postman Testing Guide

## 1. Mục tiêu

Tài liệu này hướng dẫn chạy API testing cho dự án StockFlow bằng Postman.

Phạm vi test gồm:

- Health check
- Authentication
- Product CRUD
- Role-based authorization
- Stock-in
- Stock-out
- Dashboard summary
- Inventory report
- Authentication negative tests

## 2. File cần có

```text
postman/
├── StockFlow_Local_Environment.postman_environment.json
└── StockFlow_API_Collection.postman_collection.json

docs/07_api_testing/
├── Postman_Testing_Guide.md
└── API_Test_Execution_Summary.md
```

## 3. Điều kiện trước khi chạy

Backend phải đang chạy tại:

```text
http://localhost:8080
```

Swagger có thể kiểm tra tại:

```text
http://localhost:8080/swagger-ui/index.html
```

Database phải có sẵn 2 tài khoản seed:

| Username | Password | Role |
|---|---|---|
| admin | 123456 | ADMIN |
| staff | 123456 | STAFF |

## 4. Import vào Postman

1. Mở Postman.
2. Chọn Import.
3. Import file `StockFlow_API_Collection.postman_collection.json`.
4. Import file `StockFlow_Local_Environment.postman_environment.json`.
5. Chọn environment `StockFlow Local Environment`.

## 5. Biến môi trường

| Variable | Ý nghĩa |
|---|---|
| base_url | URL backend |
| admin_token | JWT token của admin |
| staff_token | JWT token của staff |
| product_id | ID sản phẩm được tạo bởi Postman |
| product_name | Tên sản phẩm động để tránh trùng |
| stock_in_id | ID phiếu stock-in |
| stock_out_id | ID phiếu stock-out |

## 6. Thứ tự chạy khuyến nghị

Chạy theo đúng thứ tự sau để biến môi trường được tự động lưu:

1. 01 Health / Health Check
2. 02 Auth / Login - Admin Success
3. 02 Auth / Login - Staff Success
4. 03 Product - Admin / Create Product - Success
5. 03 Product - Admin / Get Product List
6. 03 Product - Admin / Search Product By Name
7. 03 Product - Admin / Get Product Detail
8. 03 Product - Admin / Update Product
9. 05 Stock-In / Create Stock-In - Success
10. 05 Stock-In / Get Stock-In List
11. 05 Stock-In / Get Stock-In Detail
12. 06 Stock-Out / Create Stock-Out - Success
13. 06 Stock-Out / Get Stock-Out List
14. 06 Stock-Out / Get Stock-Out Detail
15. 06 Stock-Out / Create Stock-Out - Quantity Exceeds Inventory
16. 07 Dashboard / Get Dashboard Summary
17. 08 Inventory Report / Get Inventory Report
18. 04 Product - Staff Authorization / Staff Create Product - Forbidden
19. 09 Authentication Negative Tests / Get Products - No Token

## 7. Cách chạy toàn bộ collection

Có thể chạy bằng Collection Runner:

1. Chọn collection `StockFlow API Collection`.
2. Chọn `Run`.
3. Chọn environment `StockFlow Local Environment`.
4. Chạy toàn bộ collection theo thứ tự folder.

## 8. Kết quả mong đợi

| Nhóm test | Kết quả mong đợi |
|---|---|
| Login hợp lệ | Trả về token và lưu vào environment |
| Login sai | Bị từ chối |
| Product Admin | Admin tạo, xem, sửa sản phẩm thành công |
| Product Staff | Staff bị chặn khi create/update/delete |
| Stock-in | Tạo giao dịch nhập kho, tăng số lượng |
| Stock-out | Tạo giao dịch xuất kho, giảm số lượng |
| Stock-out vượt tồn kho | Bị từ chối |
| Dashboard | Trả về dữ liệu tổng quan |
| Inventory Report | Trả về danh sách tồn kho và filter hoạt động |
| No token / invalid token | Bị từ chối |

## 9. Lưu ý khi có request fail

Nếu request fail hàng loạt, kiểm tra theo thứ tự:

1. Backend đã chạy chưa.
2. Environment đã chọn đúng chưa.
3. `base_url` có đúng `http://localhost:8080` không.
4. Login admin/staff có pass không.
5. `admin_token`, `staff_token`, `product_id` đã được set chưa.
6. Database có seed account chưa.
7. Endpoint thực tế trong backend có khác tên với collection không.

## 10. Commit sau khi hoàn thành

```bash
git add postman docs/07_api_testing
git commit -m "test: add postman api testing collection"
```
