# StockFlow Automation Test Plan

## 1. Document Information

| Item | Value |
|---|---|
| Project Name | StockFlow |
| Document Name | Automation Test Plan |
| Version | 1.0 |
| Prepared By | Nguyen Phuc Tan |
| Automation Scope | UI Automation Testing |
| Planned Tool | Selenium WebDriver with Java |
| Status | Draft |

## 2. Purpose

This document defines the automation testing plan for the StockFlow project.

The purpose of automation testing is to automate stable, repetitive, and high-value regression test cases after the manual testing documents are completed.

## 3. Automation Objectives

The objectives of automation testing are:

- Automate important smoke test flows.
- Automate stable regression test cases.
- Reduce repeated manual execution for common flows.
- Verify critical business flows such as login, product creation, stock-in, and stock-out.
- Verify important validation rules such as stock-out quantity not exceeding current inventory.
- Generate repeatable automation execution results for portfolio demonstration.

## 4. Automation Scope

### 4.1 In Scope

The following areas are planned for automation:

| Module | Automation Scope |
|---|---|
| Authentication | Login success, login failure, logout |
| Product Management | Create product, search product, validation |
| Stock-In | Create stock-in transaction and verify success message |
| Stock-Out | Create stock-out transaction and verify validation/business rules |
| Inventory Report | Verify page loads and report data is displayed |
| Role-Based Access | Verify Staff cannot access Admin-only pages |
| Smoke Test | Verify main pages can be opened successfully |

### 4.2 Out of Scope

The following areas are not included in the first automation phase:

| Area | Reason |
|---|---|
| Full visual UI comparison | Not required for first portfolio automation phase |
| Performance testing | Should be handled by performance tools, not Selenium |
| API automation | Planned separately with Postman/Newman or REST Assured |
| Database automation | Manual SQL validation is enough for current phase |
| Complex cross-browser testing | Initial automation will focus on Chrome only |
| Unstable UI edge cases | Automation should start with stable flows first |

## 5. Automation Tool and Technology

| Item | Planned Tool |
|---|---|
| Programming Language | Java |
| Automation Tool | Selenium WebDriver |
| Test Framework | JUnit 5 or TestNG |
| Build Tool | Maven |
| Browser | Google Chrome |
| Driver Management | WebDriverManager |
| Reporting | Surefire Report / ExtentReports later |
| Source Folder | automation/ |

## 6. Automation Candidate Selection Criteria

A test case is suitable for automation when:

- The flow is stable.
- The flow is repeated frequently.
- The flow has clear expected results.
- The test data can be prepared consistently.
- The test result can be verified automatically.
- The test case belongs to smoke or regression testing.

A test case is not suitable for early automation when:

- The UI is unstable.
- The result requires subjective visual judgment.
- The test data is difficult to prepare.
- The flow changes frequently.
- The test case is only executed once.

## 7. Automation Test Levels

| Level | Description |
|---|---|
| Smoke Automation | Verify main flows quickly before deeper testing |
| Regression Automation | Re-run stable important test cases after changes |
| UI Functional Automation | Verify UI workflows through browser actions |

## 8. Automation Test Data

| Data Type | Example |
|---|---|
| Admin Account | admin / 123456 |
| Staff Account | staff / 123456 |
| Valid Product Name | Auto Laptop Dell |
| Duplicate Product Name | Existing product name |
| Valid Stock-In Quantity | 5 |
| Valid Stock-Out Quantity | 2 |
| Invalid Stock-Out Quantity | Current quantity + 1 |
| Invalid Login Password | wrong123 |

## 9. Automation Execution Preconditions

Before running automation tests:

- Backend application is running.
- Frontend application is running.
- MySQL database is running.
- Test accounts are available.
- Chrome browser is installed.
- Test data is prepared.
- The frontend URL is accessible.
- The automation project dependencies are installed.

## 10. Planned Automation Folder Structure

```text
automation/
├── pom.xml
├── README.md
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── stockflow/
│                   ├── base/
│                   │   └── BaseTest.java
│                   ├── pages/
│                   │   ├── LoginPage.java
│                   │   ├── DashboardPage.java
│                   │   ├── ProductListPage.java
│                   │   ├── ProductFormPage.java
│                   │   ├── StockInPage.java
│                   │   └── StockOutPage.java
│                   └── tests/
│                       ├── LoginTest.java
│                       ├── ProductTest.java
│                       ├── StockInTest.java
│                       ├── StockOutTest.java
│                       └── RoleAccessTest.java
└── reports/
```

## 11. Automation Risks and Mitigation

| Risk | Impact | Mitigation |
|---|---|---|
| UI locators change | Tests fail even when feature works | Use stable selectors and Page Object Model |
| Test data conflict | Tests fail because data already exists | Use unique data or clean up test data |
| Backend/frontend not running | Automation cannot execute | Add environment checklist before running |
| Slow page loading | Tests become flaky | Use explicit waits |
| Role-based tests need different accounts | Login state may conflict | Separate tests by user role |

## 12. First Automation Priority

The first automation phase should focus on:

1. Login successfully as Admin.
2. Login fails with invalid password.
3. Admin creates product successfully.
4. Product name required validation.
5. Search product by name.
6. Create stock-in transaction successfully.
7. Create stock-out transaction successfully.
8. Verify stock-out quantity cannot exceed current quantity.
9. Staff cannot access product create page.
10. Logout successfully.

## 13. Completion Criteria

Automation planning is considered complete when:

- Automation scope is defined.
- Automation candidate test cases are listed.
- Tool and framework are selected.
- Automation folder structure is defined.
- First automation test cases are ready for implementation.

## 14. Notes

This document only covers automation planning and automation candidate selection.

The actual Selenium automation source code will be implemented in the root `automation/` folder after the `testing/08_automation_testing/` documentation is completed.
