# StockFlow Requirement Summary

## 1. Project Overview

StockFlow is a mini inventory management system designed to manage products, stock-in transactions, stock-out transactions, dashboard summary, inventory reports, and transaction history.

This project is built as a QA/Tester portfolio project. It provides enough business rules, validation rules, role-based access rules, API flows, and database changes to support manual testing, API testing, database testing, and future automation testing.

## 2. User Roles

### 2.1 Admin

Admin can:

- Log in and log out.
- View dashboard.
- View product list.
- Create products.
- Update products.
- Delete products based on business rules.
- Change product status.
- Create stock-in transactions.
- Create stock-out transactions.
- View stock-in history.
- View stock-out history.
- View inventory report.

### 2.2 Staff

Staff can:

- Log in and log out.
- View dashboard.
- View product list.
- Create stock-in transactions.
- Create stock-out transactions.
- View stock-in history.
- View stock-out history.
- View inventory report.

Staff cannot:

- Create products.
- Update products.
- Delete products.
- Change product status.
- Access Admin-only product management pages directly.
- Call Admin-only product management APIs successfully.

## 3. Functional Requirement Summary

| Requirement ID | Module | Requirement Summary | Priority |
|---|---|---|---|
| FR-AUTH-001 | Authentication | The system allows users to log in using username and password | High |
| FR-AUTH-002 | Authentication | The system allows logged-in users to log out | High |
| FR-DASH-001 | Dashboard | The system displays dashboard summary after login | Medium |
| FR-PROD-001 | Product Management | The system allows users to view product list | High |
| FR-PROD-002 | Product Management | The system allows Admin to create a new product | High |
| FR-PROD-003 | Product Management | The system allows Admin to update product information | High |
| FR-PROD-004 | Product Management | The system allows Admin to delete a product only when business rules allow | High |
| FR-PROD-005 | Product Management | The system allows Admin to change product status | Medium |
| FR-STIN-001 | Stock-In | The system allows Admin and Staff to create stock-in transactions | High |
| FR-STIN-002 | Stock-In | The system allows users to view stock-in transaction history | Medium |
| FR-STOUT-001 | Stock-Out | The system allows Admin and Staff to create stock-out transactions | High |
| FR-STOUT-002 | Stock-Out | The system allows users to view stock-out transaction history | Medium |
| FR-REP-001 | Inventory Report | The system allows users to view inventory report | Medium |

## 4. Non-Functional Requirement Summary

| Requirement ID | Category | Requirement Summary | Priority |
|---|---|---|---|
| NFR-USE-001 | Usability | The UI should be simple and easy to use | Medium |
| NFR-USE-002 | Usability | Validation messages should be clear | High |
| NFR-PER-001 | Performance | Product list should load within 3 seconds for 1,000 records | Low |
| NFR-PER-002 | Performance | Search and filter should return results within 3 seconds | Low |
| NFR-SEC-001 | Security | Users must log in before accessing the system | High |
| NFR-SEC-002 | Security | Passwords must not be stored as plain text | High |
| NFR-SEC-003 | Security | Staff users must not access Admin-only actions | High |
| NFR-SEC-004 | Security | Protected APIs must require authentication | High |
| NFR-DATA-001 | Data Integrity | Product quantity must always be accurate after stock-in and stock-out | High |
| NFR-DATA-002 | Data Integrity | Stock-out must never make product quantity negative | High |
| NFR-DATA-003 | Data Integrity | Transaction history must not be deleted when product status changes | Medium |

## 5. Business Rule Summary

| Rule ID | Business Rule | Related Module | Priority |
|---|---|---|---|
| BR-001 | Product name must be unique | Product Management | High |
| BR-002 | Product price must be greater than 0 | Product Management | High |
| BR-003 | Product quantity cannot be negative | Product Management, Stock-Out | High |
| BR-004 | Product quantity can only be changed through stock-in or stock-out | Product Management, Stock-In, Stock-Out | High |
| BR-005 | Stock-in quantity must be greater than 0 | Stock-In | High |
| BR-006 | Stock-out quantity must be greater than 0 | Stock-Out | High |
| BR-007 | Stock-out quantity cannot exceed current inventory quantity | Stock-Out | High |
| BR-008 | Inactive products cannot be used in new stock-in or stock-out transactions | Stock-In, Stock-Out | High |
| BR-009 | Products with quantity greater than 0 cannot be deleted | Product Management | High |
| BR-010 | Products with transaction history cannot be deleted | Product Management | High |
| BR-011 | Low stock means quantity is from 1 to 10 | Dashboard, Inventory Report | Medium |
| BR-012 | Out of stock means quantity equals 0 | Dashboard, Inventory Report | Medium |
| BR-013 | Staff cannot create, update, or delete products | Role-Based Access | High |
| BR-014 | User cannot access protected pages without login | Authentication, Security | High |
| BR-015 | User cannot access Admin-only pages as Staff | Role-Based Access | High |

## 6. Main Modules

| Module | Description |
|---|---|
| Authentication | Login, logout, token handling, and protected route behavior |
| Dashboard | Summary of product and inventory status |
| Product Management | Create, view, update, delete, search, and filter products |
| Stock-In | Create and view stock-in transactions |
| Stock-Out | Create and view stock-out transactions |
| Transaction History | View and filter stock-in and stock-out history |
| Inventory Report | View current inventory status and filter report data |
| Role-Based Access | Verify permission differences between Admin and Staff |

## 7. Main Validation Rules

| Area | Validation Rule |
|---|---|
| Login | Username is required |
| Login | Password is required |
| Login | Invalid username or password should be rejected |
| Product | Product name is required |
| Product | Product name must be unique |
| Product | Category is required |
| Product | Price must be greater than 0 |
| Product | Initial quantity must be greater than or equal to 0 |
| Product | Status must be Active or Inactive |
| Stock-In | Product is required |
| Stock-In | Product must exist |
| Stock-In | Product must be Active |
| Stock-In | Quantity must be greater than 0 |
| Stock-Out | Product is required |
| Stock-Out | Product must exist |
| Stock-Out | Product must be Active |
| Stock-Out | Quantity must be greater than 0 |
| Stock-Out | Quantity must not exceed current inventory quantity |

## 8. Acceptance Criteria Summary

The project is considered complete when:

- Users can log in and log out.
- Admin can create, update, delete, and view products.
- Staff can view products but cannot create, update, or delete products.
- Users can create stock-in transactions.
- Users can create stock-out transactions.
- Product quantity increases correctly after stock-in.
- Product quantity decreases correctly after stock-out.
- The system prevents stock-out quantity from exceeding current inventory.
- The system prevents product quantity from becoming negative.
- The system shows dashboard summary correctly.
- The system shows inventory report correctly.
- The system shows stock-in and stock-out history correctly.
- Protected APIs require authentication.
- Admin-only APIs reject Staff users.
- Database records are updated correctly after key actions.
- Manual test cases can be executed and recorded.

## 9. Out of Scope

The following features are not included in StockFlow version 1:

- User registration
- Password reset
- Supplier management
- Customer management
- Purchase order management
- Sales order management
- Barcode scanning
- Export Excel/PDF
- Email notification
- Multi-warehouse management
- Payment management

## 10. Notes for Testing

Testing should focus on:

- Business rule validation.
- Role-based access control.
- Product quantity accuracy.
- Stock-in and stock-out transaction integrity.
- UI validation messages.
- API response format.
- Database consistency after successful and failed transactions.