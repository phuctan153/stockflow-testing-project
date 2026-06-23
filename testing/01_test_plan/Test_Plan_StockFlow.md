# StockFlow Test Plan

## 1. Document Information

| Item          | Value                                         |
| ------------- | --------------------------------------------- |
| Project Name  | StockFlow                                     |
| Document Name | Test Plan                                     |
| Version       | 1.0                                           |
| Prepared By   | Nguyen Phuc Tan                               |
| Testing Type  | Manual Testing, API Testing, Database Testing |
| Status        | Draft                                         |

## 2. Project Overview

StockFlow is a mini inventory management system designed to manage products, stock-in transactions, stock-out transactions, dashboard summary, inventory reports, and transaction history.

This project is used as a QA/Tester portfolio project. The system includes enough business rules, validation rules, role-based access rules, API flows, and database changes to support realistic testing activities.

## 3. Test Objectives

The objectives of testing are:

* Verify that users can log in and log out successfully.
* Verify that Admin and Staff permissions work correctly.
* Verify that product management functions work according to requirements.
* Verify that stock-in transactions increase product quantity correctly.
* Verify that stock-out transactions decrease product quantity correctly.
* Verify that stock-out quantity cannot exceed current inventory quantity.
* Verify that inactive products cannot be used in new stock-in or stock-out transactions.
* Verify that product deletion follows business rules.
* Verify that dashboard summary displays correct data.
* Verify that inventory report displays correct stock status.
* Verify that transaction history is saved and displayed correctly.
* Verify that API responses follow the expected format.
* Verify that database records are updated correctly after key actions.

## 4. Test Scope

### 4.1 In Scope

The following modules are included in testing:

| Module             | Scope                                                     |
| ------------------ | --------------------------------------------------------- |
| Authentication     | Login, logout, protected route behavior                   |
| Dashboard          | Dashboard summary cards and refresh behavior              |
| Product Management | Create, view, update, delete, search, filter products     |
| Stock-In           | Create stock-in transaction and verify quantity increase  |
| Stock-Out          | Create stock-out transaction and verify quantity decrease |
| Stock-In History   | View, search, filter, and view transaction detail         |
| Stock-Out History  | View, search, filter, and view transaction detail         |
| Inventory Report   | View report, filter by category, filter by stock status   |
| Role-Based Access  | Admin and Staff permission validation                     |
| API Testing        | Authentication, authorization, validation, business logic |
| Database Testing   | Product data, transaction records, inventory quantity     |

### 4.2 Out of Scope

The following features are not included in StockFlow version 1 testing scope:

| Feature                                   | Reason                                       |
| ----------------------------------------- | -------------------------------------------- |
| User registration                         | Not implemented in version 1                 |
| Password reset                            | Not implemented in version 1                 |
| Supplier management                       | Out of scope                                 |
| Customer management                       | Out of scope                                 |
| Purchase order management                 | Out of scope                                 |
| Sales order management                    | Out of scope                                 |
| Barcode scanning                          | Out of scope                                 |
| Export Excel/PDF                          | Out of scope                                 |
| Email notification                        | Out of scope                                 |
| Multi-warehouse management                | Out of scope                                 |
| Payment management                        | Out of scope                                 |
| Performance testing with large-scale load | Not the main focus of this portfolio version |

## 5. Test Strategy

Testing will be performed using a combination of manual testing, API testing, and database testing.

### 5.1 Manual Testing

Manual testing will be used to verify:

* UI behavior
* Navigation
* Form validation
* Error messages
* Role-based access from the frontend
* Main business flows
* End-to-end user flows

### 5.2 API Testing

API testing will be performed using Postman to verify:

* HTTP methods
* Request body validation
* Response body format
* Authentication
* Authorization
* Business rules
* Error messages
* Status codes

### 5.3 Database Testing

Database testing will be used to verify:

* Product data is inserted correctly.
* Product data is updated correctly.
* Product quantity increases after stock-in.
* Product quantity decreases after stock-out.
* Product quantity never becomes negative.
* Stock-in transaction records are saved correctly.
* Stock-out transaction records are saved correctly.
* Delete product rules work correctly.

### 5.4 Regression Testing

Regression testing will be performed after bug fixes or feature changes to ensure existing features still work correctly.

### 5.5 Smoke Testing

Smoke testing will be performed before detailed testing to verify that the main application flows are working.

Smoke test areas:

* Login as Admin
* Login as Staff
* View dashboard
* View product list
* Create product as Admin
* Create stock-in transaction
* Create stock-out transaction
* View inventory report
* Logout

## 6. Test Types

| Test Type                 | Description                                                     |
| ------------------------- | --------------------------------------------------------------- |
| Functional Testing        | Verify features against requirements                            |
| UI Testing                | Verify layout, buttons, forms, tables, messages, and navigation |
| Validation Testing        | Verify required fields, invalid data, and boundary values       |
| Role-Based Access Testing | Verify Admin and Staff permissions                              |
| API Testing               | Verify endpoint behavior, response data, and authorization      |
| Database Testing          | Verify database records and data integrity                      |
| Smoke Testing             | Verify main flows before detailed testing                       |
| Regression Testing        | Re-test important flows after fixes or changes                  |

## 7. Test Environment

| Item             | Value                                       |
| ---------------- | ------------------------------------------- |
| Application      | StockFlow                                   |
| Backend          | Spring Boot                                 |
| Frontend         | React, TypeScript, Vite, Ant Design         |
| Database         | MySQL                                       |
| API Testing Tool | Postman                                     |
| Browser          | Google Chrome                               |
| Backend URL      | http://localhost:8080                       |
| Frontend URL     | http://localhost:3000                       |
| Swagger URL      | http://localhost:8080/swagger-ui/index.html |
| Operating System | Windows                                     |
| Source Control   | Git, GitHub                                 |

## 8. Test Accounts

| Username | Password | Role  | Permission Summary                                           |
| -------- | -------- | ----- | ------------------------------------------------------------ |
| admin    | 123456   | ADMIN | Can manage products and create inventory transactions        |
| staff    | 123456   | STAFF | Can view products and create stock-in/stock-out transactions |

## 9. Entry Criteria

Testing can start when:

* Backend application can run successfully.
* Frontend application can run successfully.
* MySQL database is available.
* Admin and Staff seed accounts are available.
* Main APIs are implemented.
* Main frontend pages are implemented.
* Test environment is configured.
* Requirement summary is available.
* Test scenarios or draft test cases are available.

## 10. Exit Criteria

Testing can be considered complete when:

* All planned high-priority test cases are executed.
* All critical and high-severity bugs are fixed or documented.
* Main business rules are verified.
* Role-based access is verified.
* API testing is completed for main endpoints.
* Database validation is completed for main data flows.
* Test execution result is recorded.
* Test summary report is completed.

## 11. Test Deliverables

| Deliverable                      | Location                                                   |
| -------------------------------- | ---------------------------------------------------------- |
| Requirement Summary              | testing/00_requirement/Requirement_Summary.md              |
| Requirement Traceability Summary | testing/00_requirement/Requirement_Traceability_Summary.md |
| Test Plan                        | testing/01_test_plan/Test_Plan_StockFlow.md                |
| Test Scenarios                   | testing/02_test_scenarios/                                 |
| Manual Test Cases                | testing/03_test_cases/                                     |
| Bug Reports                      | testing/04_bug_reports/                                    |
| Test Execution Report            | testing/05_test_execution/                                 |
| API Test Cases                   | testing/06_api_testing/                                    |
| Database Testing Checklist       | testing/07_database_testing/                               |
| Automation Testing Documents     | testing/08_automation_testing/                             |

## 12. Test Data

Main test data includes:

| Data Type            | Example                                                              |
| -------------------- | -------------------------------------------------------------------- |
| Admin Account        | admin / 123456                                                       |
| Staff Account        | staff / 123456                                                       |
| Valid Product        | Laptop Dell Inspiron                                                 |
| Duplicate Product    | Product with an existing name                                        |
| Active Product       | Product with ACTIVE status                                           |
| Inactive Product     | Product with INACTIVE status                                         |
| In-stock Product     | Quantity greater than 10                                             |
| Low-stock Product    | Quantity from 1 to 10                                                |
| Out-of-stock Product | Quantity equals 0                                                    |
| Invalid Quantity     | 0, negative number, or stock-out quantity greater than current stock |
| Invalid Price        | 0 or negative number                                                 |

## 13. Main Business Rules to Validate

| Rule ID | Business Rule                                                              |
| ------- | -------------------------------------------------------------------------- |
| BR-001  | Product name must be unique                                                |
| BR-002  | Product price must be greater than 0                                       |
| BR-003  | Product quantity cannot be negative                                        |
| BR-004  | Product quantity can only be changed through stock-in or stock-out         |
| BR-005  | Stock-in quantity must be greater than 0                                   |
| BR-006  | Stock-out quantity must be greater than 0                                  |
| BR-007  | Stock-out quantity cannot exceed current inventory quantity                |
| BR-008  | Inactive products cannot be used in new stock-in or stock-out transactions |
| BR-009  | Products with quantity greater than 0 cannot be deleted                    |
| BR-010  | Products with transaction history cannot be deleted                        |
| BR-011  | Low stock means quantity is from 1 to 10                                   |
| BR-012  | Out of stock means quantity equals 0                                       |
| BR-013  | Staff cannot create, update, or delete products                            |
| BR-014  | User cannot access protected pages without login                           |
| BR-015  | User cannot access Admin-only pages as Staff                               |

## 14. Test Schedule

| Phase   | Activity              | Output                                |
| ------- | --------------------- | ------------------------------------- |
| Phase 1 | Requirement review    | Requirement summary and traceability  |
| Phase 2 | Test planning         | Test plan                             |
| Phase 3 | Test design           | Test scenarios and test cases         |
| Phase 4 | Manual test execution | Test execution result                 |
| Phase 5 | Bug reporting         | Bug reports                           |
| Phase 6 | API testing           | API test cases and API report         |
| Phase 7 | Database testing      | Database checklist and SQL validation |
| Phase 8 | Test closure          | Test summary report                   |

## 15. Roles and Responsibilities

| Role      | Responsibility                                                                                                  |
| --------- | --------------------------------------------------------------------------------------------------------------- |
| Tester    | Analyze requirements, design test scenarios, write test cases, execute tests, report bugs, prepare test summary |
| Developer | Fix reported bugs and support technical investigation                                                           |
| Reviewer  | Review testing documents and test results                                                                       |

For this portfolio project, the Tester and Developer roles are performed by the project owner.

## 16. Risk and Mitigation

| Risk                                   | Impact                                          | Mitigation                                        |
| -------------------------------------- | ----------------------------------------------- | ------------------------------------------------- |
| Requirement changes during testing     | Test cases may need updates                     | Keep requirement summary and traceability updated |
| Test data is changed during execution  | Test results may be inconsistent                | Prepare stable test data before execution         |
| Backend or frontend not running        | Testing cannot be executed                      | Verify environment before test execution          |
| Database contains old data             | Test result may be inaccurate                   | Reset or prepare database before testing          |
| Role permission issue is missed        | Security-related bug may remain                 | Test both Admin and Staff accounts                |
| API and UI validation are inconsistent | Different behavior between frontend and backend | Verify validation from both UI and API levels     |

## 17. Defect Management

Bugs will be documented with the following information:

* Bug ID
* Module
* Summary
* Description
* Steps to reproduce
* Test data
* Expected result
* Actual result
* Severity
* Priority
* Status
* Environment
* Screenshot or attachment
* Reported date
* Fixed date
* Retest result

Bug severity levels:

| Severity | Description                                              |
| -------- | -------------------------------------------------------- |
| Critical | System crash, data loss, or main flow completely blocked |
| High     | Important business rule fails                            |
| Medium   | Feature works incorrectly but has workaround             |
| Low      | Minor UI issue or text issue                             |

Bug priority levels:

| Priority | Description                                |
| -------- | ------------------------------------------ |
| High     | Should be fixed as soon as possible        |
| Medium   | Should be fixed in normal development flow |
| Low      | Can be fixed later                         |

## 18. Test Completion Criteria

Testing will be completed when:

* Main functional flows are tested.
* High-priority business rules are tested.
* Role-based access is tested.
* Main API endpoints are tested.
* Main database validations are completed.
* Bugs are recorded clearly.
* Test execution result is summarized.
* Final test conclusion is prepared.

## 19. Approval

| Name            | Role                   | Status   |
| --------------- | ---------------------- | -------- |
| Nguyen Phuc Tan | Tester / Project Owner | Prepared |