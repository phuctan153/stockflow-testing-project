# StockFlow UI Automation Tests

This folder contains UI automation test scripts for the **StockFlow – Mini Inventory Management System** project.

The automation suite verifies the main business-critical flows of the system, including login, product management, stock-in, stock-out, stock-out validation, and role-based access control.

---

## 1. Automation Scope

| Test Case ID | Test Case Name | Module | Priority | Status |
|---|---|---|---|---|
| ATC-001 | Login successfully with admin account | Authentication | High | Implemented |
| ATC-002 | Login fails with invalid password | Authentication | High | Implemented |
| ATC-003 | Login validation with empty username/password | Authentication | High | Implemented |
| ATC-004 | Admin creates product successfully | Product Management | High | Implemented |
| ATC-005 | Create product with empty name shows validation | Product Management | Medium | Implemented |
| ATC-006 | Search product by name | Product Management | Medium | Implemented |
| ATC-007 | Create stock-in transaction successfully | Stock-In | High | Implemented |
| ATC-008 | Create stock-out transaction successfully | Stock-Out | High | Implemented |
| ATC-009 | Stock-out quantity cannot exceed current quantity | Stock-Out | High | Implemented |
| ATC-010 | Staff cannot access product create page | Role-Based Access | High | Implemented |

---

## 2. Technology Stack

| Tool | Purpose |
|---|---|
| Java 21 | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test framework |
| WebDriverManager | Browser driver management |
| Maven | Build and test execution |
| Google Chrome | Test browser |

---

## 3. Project Structure

```text
automation/
├── pom.xml
├── testng.xml
├── README.md
└── src/
    └── test/
        ├── java/com/stockflow/
        │   ├── base/
        │   ├── pages/
        │   ├── tests/
        │   └── utils/
        └── resources/
            └── config.properties
```

---

## 4. Test Environment

| Component | Value |
|---|---|
| Frontend URL | `http://localhost:3000` |
| Backend URL | `http://localhost:8080` |
| Database | MySQL |
| Browser | Google Chrome |
| Operating System | Windows 11 |
| Execution Type | Local |

---

## 5. Test Accounts

| Username | Password | Role |
|---|---|---|
| admin | 123456 | ADMIN |
| staff | 123456 | STAFF |

---

## 6. Prerequisites

Before running automation tests, make sure the following services are running:

1. MySQL database
2. StockFlow backend at `http://localhost:8080`
3. StockFlow frontend at `http://localhost:3000`
4. Google Chrome browser

---

## 7. Configuration

The test configuration file is located at:

```text
src/test/resources/config.properties
```

Example configuration:

```properties
baseUrl=http://localhost:3000

adminUsername=admin
adminPassword=123456

staffUsername=staff
staffPassword=123456

invalidPassword=wrong123
```

---

## 8. How to Run Tests

Open terminal in the `automation` folder:

```bash
cd automation
```

Run all automation tests:

```bash
mvn clean test
```

Run a specific test class:

```bash
mvn -Dtest=LoginTest test
mvn -Dtest=ProductTest test
mvn -Dtest=StockInTest test
mvn -Dtest=StockOutTest test
mvn -Dtest=RoleAccessTest test
```

---

## 9. Test Report Location

After running tests, Maven Surefire and TestNG reports are generated in:

```text
target/surefire-reports/
```

Important report files:

```text
target/surefire-reports/TestSuite.txt
target/surefire-reports/index.html
target/surefire-reports/emailable-report.html
target/surefire-reports/testng-results.xml
```

---

## 10. Latest Recorded Execution Result

The latest Maven Surefire report included in this project shows no failed automation test.

| Report | Tests Run | Passed | Failures | Errors | Skipped | Status |
|---|---:|---:|---:|---:|---:|---|
| `TestSuite.txt` | 8 | 8 | 0 | 0 | 0 | Passed |
| `RoleAccessTest.txt` | 1 | 1 | 0 | 0 | 0 | Passed |
| `StockOutTest.txt` | 2 | 2 | 0 | 0 | 0 | Passed |

Note: Run `mvn clean test` again after code changes to generate the newest local execution result.

---

## 11. Notes

- The automation tests use Page Object Model structure.
- Test data is generated dynamically by timestamp to avoid duplicate product name issues.
- Backend and frontend must be running before executing tests.
- Some frontend elements use fixed `id`, `className`, or `popupClassName` to make Selenium locators more stable.
- These tests are designed for basic regression testing and testing portfolio demonstration.
