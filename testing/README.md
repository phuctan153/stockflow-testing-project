# StockFlow Testing Portfolio

## 1. Overview

StockFlow is a mini inventory management system built as a personal QA/Tester portfolio project.

The system supports product management, stock-in transactions, stock-out transactions, dashboard summary, inventory report, transaction history, and role-based access control.

This `testing` folder contains all testing artifacts created for the StockFlow project, including requirement analysis, test plan, test scenarios, manual test cases, bug reports, test execution report, API testing documents, database testing documents, and future automation testing documents.

## 2. Testing Objectives

The main objectives of testing are:

- Verify that all main business flows work correctly.
- Validate product, stock-in, and stock-out business rules.
- Ensure product quantity is updated accurately after inventory transactions.
- Verify Admin and Staff role permissions.
- Validate UI behavior, form validation, and error messages.
- Verify API response format, authentication, authorization, and business logic.
- Verify database records and data integrity after key actions.

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

## 4. Test Types

| Test Type | Purpose |
|---|---|
| Functional Testing | Verify that each feature works according to requirements |
| UI Testing | Verify layout, buttons, fields, tables, messages, and navigation |
| Validation Testing | Verify required fields, invalid input, boundary values, and error messages |
| Role-Based Access Testing | Verify Admin and Staff permissions |
| API Testing | Verify endpoints, request body, response body, status codes, authentication, and authorization |
| Database Testing | Verify product data, transaction records, and inventory quantity changes |
| Regression Testing | Re-test main flows after bug fixes or changes |
| Smoke Testing | Quickly verify that major features work before detailed testing |

## 5. Test Environment

| Item | Value |
|---|---|
| Application | StockFlow |
| Backend | Spring Boot |
| Frontend | React, TypeScript, Vite, Ant Design |
| Database | MySQL |
| API Tool | Postman |
| Browser | Google Chrome |
| Backend URL | `http://localhost:8080` |
| Frontend URL | `http://localhost:3000` |
| Swagger URL | `http://localhost:8080/swagger-ui/index.html` |

## 6. Test Accounts

| Username | Password | Role | Description |
|---|---|---|---|
| admin | 123456 | ADMIN | Can manage products and inventory transactions |
| staff | 123456 | STAFF | Can view products and create stock-in/stock-out transactions |

## 7. Folder Structure

The testing folder is organized as follows:

| Folder | Purpose |
|---|---|
| `00_requirement/` | Requirement summary and traceability documents |
| `01_test_plan/` | Test plan and test estimation |
| `02_test_scenarios/` | Test scenarios and requirement traceability matrix |
| `03_test_cases/` | Manual test cases and test data |
| `04_bug_reports/` | Bug report template and sample bug reports |
| `05_test_execution/` | Test execution result and summary report |
| `06_api_testing/` | API test cases, checklist, and report |
| `07_database_testing/` | Database checklist and SQL validation |
| `08_automation_testing/` | Automation testing documents for future Selenium tests |

## 8. Testing Deliverables

| Folder | Deliverable | Description |
|---|---|---|
| 00_requirement | Requirement documents | Requirement summary and requirement traceability |
| 01_test_plan | Test plan | Scope, strategy, environment, risks, entry criteria, and exit criteria |
| 02_test_scenarios | Test scenarios | High-level scenarios grouped by module |
| 03_test_cases | Manual test cases | Detailed manual test cases with steps, data, and expected results |
| 04_bug_reports | Bug reports | Bug report template and sample bug reports |
| 05_test_execution | Test execution report | Test execution status, pass/fail summary, and final conclusion |
| 06_api_testing | API testing documents | API test cases, checklist, and report |
| 07_database_testing | Database testing documents | SQL validation and database testing checklist |
| 08_automation_testing | Automation testing documents | Automation plan, automation cases, and report for future Selenium tests |

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

## 11. Testing Status

| Area | Status |
|---|---|
| Requirement Review | Planned |
| Test Plan | Planned |
| Test Scenarios | Planned |
| Manual Test Cases | Planned |
| Bug Reports | Planned |
| Test Execution | Planned |
| API Testing | Planned |
| Database Testing | Planned |
| Automation Testing | Planned for later |

## 12. Notes

This testing portfolio is designed to demonstrate practical QA skills, including requirement analysis, test design, manual test execution, API testing, database validation, defect reporting, and future automation testing.

The project is suitable for showcasing manual testing, API testing, database testing, and basic automation testing ability in a fresher QA/Tester portfolio.
