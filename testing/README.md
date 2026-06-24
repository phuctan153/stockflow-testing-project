# StockFlow Testing Portfolio

## 1. Overview

StockFlow is a mini inventory management system built as a personal QA/Tester portfolio project.

The system supports product management, stock-in transactions, stock-out transactions, dashboard summary, inventory report, transaction history, and role-based access control.

This `testing` folder contains all testing artifacts created for the StockFlow project, including requirement analysis, test plan, test scenarios, manual test cases, bug reports, test execution report, API testing documents, database testing documents, and automation testing documents.

---

## 2. Testing Objectives

The main objectives of testing are:

- Verify that all main business flows work correctly.
- Validate product, stock-in, and stock-out business rules.
- Ensure product quantity is updated accurately after inventory transactions.
- Verify Admin and Staff role permissions.
- Validate UI behavior, form validation, and error messages.
- Verify API response format, authentication, authorization, and business logic.
- Verify database records and data integrity after key actions.
- Verify stable regression flows using UI automation.

---

## 3. Testing Scope

### 3.1 In Scope

- Authentication
- Dashboard
- Product Management
- Stock-In Management
- Stock-Out Management
- Stock-In History
- Stock-Out History
- Inventory Report
- Role-Based Access Control
- UI Validation
- API Testing
- Database Testing
- UI Automation Testing

### 3.2 Out of Scope

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

---

## 4. Test Types

| Test Type | Purpose |
|---|---|
| Functional Testing | Verify that each feature works according to requirements |
| UI Testing | Verify layout, buttons, fields, tables, messages, and navigation |
| Validation Testing | Verify required fields, invalid input, boundary values, and error messages |
| Role-Based Access Testing | Verify Admin and Staff permissions |
| API Testing | Verify endpoints, request body, response body, status codes, authentication, and authorization |
| Database Testing | Verify product data, transaction records, and inventory quantity changes |
| Automation Testing | Verify stable regression flows using Selenium |
| Regression Testing | Re-test main flows after bug fixes or changes |
| Smoke Testing | Quickly verify that major features work before detailed testing |

---

## 5. Test Environment

| Item | Value |
|---|---|
| Application | StockFlow |
| Backend | Spring Boot |
| Frontend | React, TypeScript, Vite, Ant Design |
| Database | MySQL |
| API Tool | Postman |
| Automation Tool | Selenium Java, TestNG |
| Browser | Google Chrome |
| Backend URL | `http://localhost:8080` |
| Frontend URL | `http://localhost:3000` |
| Swagger URL | `http://localhost:8080/swagger-ui/index.html` |

---

## 6. Test Accounts

| Username | Password | Role | Description |
|---|---|---|---|
| admin | 123456 | ADMIN | Can manage products and inventory transactions |
| staff | 123456 | STAFF | Can view products and create stock-in/stock-out transactions |

---

## 7. Folder Structure

| Folder | Purpose |
|---|---|
| `00_requirement/` | Requirement summary and traceability documents |
| `01_test_plan/` | Test plan and test estimation |
| `02_test_scenarios/` | Test scenarios and requirement traceability matrix |
| `03_test_cases/` | Manual test cases and test data |
| `04_bug_reports/` | Bug report template and sample bug reports |
| `05_test_execution/` | Test execution result and summary report |
| `06_api_testing/` | Postman guide and API execution summary |
| `07_automation_testing/` | Automation testing documents |

---

## 8. Testing Deliverables

| Folder | Deliverable | Description | Status |
|---|---|---|---|
| 00_requirement | Requirement documents | Requirement summary and requirement traceability | Completed |
| 01_test_plan | Test plan | Scope, strategy, environment, risks, entry criteria, and exit criteria | Completed |
| 02_test_scenarios | Test scenarios | High-level scenarios grouped by module | Completed |
| 03_test_cases | Manual test cases | Detailed manual test cases with steps, data, and expected results | Completed |
| 04_bug_reports | Bug reports | Bug report template and sample bug reports | Completed |
| 05_test_execution | Test execution report | Test execution status, pass/fail summary, and final conclusion | Completed |
| 06_api_testing | API testing documents | Postman guide and API execution summary | Completed |
| 07_automation_testing | Automation testing documents | Automation plan, automation cases, and report | Completed |

---

## 9. Main Business Rules to Test

| Rule ID | Business Rule |
|---|---|
| BR-001 | Product name must be unique |
| BR-002 | Product price must be greater than 0 |
| BR-003 | Product quantity cannot be negative |
| BR-004 | Product quantity can only be changed through stock-in or stock-out |
| BR-005 | Stock-in quantity must be greater than 0 |
| BR-006 | Stock-out quantity must be greater than 0 |
| BR-007 | Stock-out quantity cannot exceed current inventory quantity |
| BR-008 | Inactive products cannot be used in new stock-in or stock-out transactions |
| BR-009 | Products with quantity greater than 0 cannot be deleted |
| BR-010 | Products with transaction history cannot be deleted |
| BR-011 | Low stock means quantity is from 1 to 10 |
| BR-012 | Out of stock means quantity equals 0 |
| BR-013 | Staff cannot create, update, or delete products |
| BR-014 | User cannot access protected pages without login |
| BR-015 | User cannot access Admin-only pages as Staff |

---

## 10. Main Modules Covered

| Module | Description |
|---|---|
| Authentication | Login, logout, and protected route behavior |
| Dashboard | Product and inventory summary |
| Product Management | Create, view, update, delete, search, and filter products |
| Stock-In | Create stock-in transactions and verify quantity increase |
| Stock-Out | Create stock-out transactions and verify quantity decrease |
| Transaction History | View and filter stock-in/stock-out history |
| Inventory Report | View inventory status and filter report data |
| Role-Based Access | Verify Admin and Staff permissions |

---

## 11. API Testing Result

Postman collection location:

```text
postman/StockFlow_API_Collection.postman_collection.json
```

Postman environment location:

```text
postman/StockFlow_Local_Environment.postman_environment.json
```

| Result | Count |
|---|---:|
| Total Requests | 36 |
| Passed | 36 |
| Failed | 0 |
| Blocked | 0 |
| Not Run | 0 |

Detailed report:

```text
testing/06_api_testing/API_Test_Execution_Summary.md
```

---

## 12. Testing Status

| Area | Status |
|---|---|
| Requirement Review | Completed |
| Test Plan | Completed |
| Test Scenarios | Completed |
| Manual Test Cases | Completed |
| Bug Reports | Completed |
| Test Execution | Completed |
| API Testing | Completed |
| Database Testing | Completed |
| Automation Testing | Completed |

---

## 13. Notes

This testing portfolio demonstrates practical QA skills, including requirement analysis, test design, manual test execution, API testing, database validation, defect reporting, and UI automation testing.

The project is suitable for showcasing manual testing, API testing, database testing, and basic automation testing ability in a fresher QA/Tester portfolio.
