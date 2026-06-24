# StockFlow API Test Execution Summary

## 1. Thông tin chung

| Item | Detail |
|---|---|
| Project | StockFlow – Mini Inventory Management System |
| Test Type | API Testing |
| Tool | Postman |
| Environment | Local |
| Base URL | http://localhost:8080 |
| Tester | Nguyen Phuc Tan |
| Execution Date | 2026-06-24 |

## 2. Phạm vi kiểm thử

| Module | Scope |
|---|---|
| Health | Kiểm tra backend running |
| Auth | Login admin, login staff, login sai password |
| Product | CRUD, search, validation, authorization |
| Stock-In | Create, list, search, detail, invalid quantity |
| Stock-Out | Create, list, search, detail, exceed inventory, invalid quantity |
| Dashboard | Summary API |
| Inventory Report | Report API và filter |
| Security | No token, invalid token, staff forbidden |

## 3. Tổng hợp request

| Folder | Number of Requests |
|---|---:|
| 01 Health | 1 |
| 02 Auth | 3 |
| 03 Product - Admin | 9 |
| 04 Product - Staff Authorization | 3 |
| 05 Stock-In | 5 |
| 06 Stock-Out | 6 |
| 07 Dashboard | 1 |
| 08 Inventory Report | 4 |
| 09 Authentication Negative Tests | 4 |
| Total | 36 |

## 4. Kết quả thực thi

| Result | Count |
|---|---:|
| Passed | TBD |
| Failed | TBD |
| Blocked | TBD |
| Not Run | TBD |

## 5. Các điểm đã được cover

| Area | Covered |
|---|---|
| Status code validation | Yes |
| Response body validation | Yes |
| Success response format | Yes |
| Error response format | Yes |
| JWT token saving | Yes |
| Admin authorization | Yes |
| Staff authorization | Yes |
| Product validation | Yes |
| Stock-in business rules | Yes |
| Stock-out business rules | Yes |
| Inventory report filters | Yes |

## 6. Ghi chú lỗi phát hiện

| Bug ID | Module | Description | Severity | Status |
|---|---|---|---|---|
| TBD | TBD | TBD | TBD | TBD |

## 7. Kết luận

Postman collection đã cover các API chính của StockFlow, bao gồm luồng positive, negative, validation, authentication và authorization.

Sau khi chạy collection, cập nhật số lượng Passed / Failed / Blocked / Not Run ở mục 4.
