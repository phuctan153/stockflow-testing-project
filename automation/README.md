# StockFlow UI Automation Tests

This folder contains UI automation test scripts for the **StockFlow – Mini Inventory Management System** project.

The automation test suite is built to verify the main business-critical flows of the system, including login, product management, stock-in, stock-out, stock-out validation, and role-based access control.

---

## 1. Automation Scope

The automation suite focuses on stable and important user flows that are suitable for regression testing.

| Test Case ID | Test Case Name                                    | Module             | Priority |
| ------------ | ------------------------------------------------- | ------------------ | -------- |
| ATC-001      | Login successfully with admin account             | Authentication     | High     |
| ATC-002      | Login fails with invalid password                 | Authentication     | High     |
| ATC-003      | Login validation with empty username/password     | Authentication     | High     |
| ATC-004      | Admin creates product successfully                | Product Management | High     |
| ATC-005      | Create product with empty name shows validation   | Product Management | Medium   |
| ATC-006      | Search product by name                            | Product Management | Medium   |
| ATC-007      | Create stock-in transaction successfully          | Stock-In           | High     |
| ATC-008      | Create stock-out transaction successfully         | Stock-Out          | High     |
| ATC-009      | Stock-out quantity cannot exceed current quantity | Stock-Out          | High     |
| ATC-010      | Staff cannot access product create page           | Role-Based Access  | High     |

---

## 2. Technology Stack

| Tool               | Purpose                   |
| ------------------ | ------------------------- |
| Java 21            | Programming language      |
| Selenium WebDriver | Browser automation        |
| TestNG             | Test framework            |
| WebDriverManager   | Browser driver management |
| Maven              | Build and test execution  |
| Google Chrome      | Test browser              |

---

## 3. Project Structure

```text
automation/
├── pom.xml
├── testng.xml
├── README.md
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── stockflow/
        │           ├── base/
        │           │   └── BaseTest.java
        │           ├── pages/
        │           │   ├── LoginPage.java
        │           │   ├── ProductListPage.java
        │           │   ├── ProductFormPage.java
        │           │   ├── StockInPage.java
        │           │   └── StockOutPage.java
        │           ├── tests/
        │           │   ├── LoginTest.java
        │           │   ├── ProductTest.java
        │           │   ├── StockInTest.java
        │           │   ├── StockOutTest.java
        │           │   └── RoleAccessTest.java
        │           └── utils/
        │               └── TestData.java
        └── resources/
            └── config.properties
```

---

## 4. Test Environment

| Component        | Value                 |
| ---------------- | --------------------- |
| Frontend URL     | http://localhost:3000 |
| Backend URL      | http://localhost:8080 |
| Database         | MySQL                 |
| Browser          | Google Chrome         |
| Operating System | Windows 11            |
| Execution Type   | Local                 |

---

## 5. Test Accounts

| Username | Password | Role  |
| -------- | -------- | ----- |
| admin    | 123456   | ADMIN |
| staff    | 123456   | STAFF |

---

## 6. Prerequisites

Before running automation tests, make sure the following services are running:

1. MySQL database
2. StockFlow backend
3. StockFlow frontend

Backend should be available at:

```text
http://localhost:8080
```

Frontend should be available at:

```text
http://localhost:3000
```

The automation tests use real browser execution, so Google Chrome must be installed.

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

Run tests using the TestNG suite:

```bash
mvn test
```

---

## 9. TestNG Suite

The test suite is configured in:

```text
testng.xml
```

Example:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">

<suite name="StockFlow Automation Test Suite">
    <test name="StockFlow UI Automation Tests">
        <classes>
            <class name="com.stockflow.tests.LoginTest"/>
            <class name="com.stockflow.tests.ProductTest"/>
            <class name="com.stockflow.tests.StockInTest"/>
            <class name="com.stockflow.tests.StockOutTest"/>
            <class name="com.stockflow.tests.RoleAccessTest"/>
        </classes>
    </test>
</suite>
```

---

## 10. Test Report Location

After running tests, Maven Surefire and TestNG reports are generated in:

```text
target/surefire-reports/
```

Important report files:

```text
target/surefire-reports/index.html
target/surefire-reports/emailable-report.html
target/surefire-reports/testng-results.xml
```

---

## 11. Main Verified Business Rules

| Business Rule                                               | Verified By |
| ----------------------------------------------------------- | ----------- |
| User can login with valid credentials                       | ATC-001     |
| User cannot login with invalid password                     | ATC-002     |
| Username and password are required                          | ATC-003     |
| Admin can create product                                    | ATC-004     |
| Product name is required                                    | ATC-005     |
| Product search works correctly                              | ATC-006     |
| Stock-in increases product quantity                         | ATC-007     |
| Stock-out decreases product quantity                        | ATC-008     |
| Stock-out quantity cannot exceed current inventory quantity | ATC-009     |
| Staff cannot access Admin-only product create page          | ATC-010     |

---

## 12. Notes

* The automation tests use Page Object Model structure.
* Test data is generated dynamically by timestamp to avoid duplicate product name issues.
* Backend and frontend must be running before executing tests.
* Some frontend elements use fixed `id`, `className`, or `popupClassName` to make Selenium locators more stable.
* These tests are designed for basic regression testing and testing portfolio demonstration.

---

## 13. Current Execution Summary

| Metric                      | Count |
| --------------------------- | ----: |
| Total Automation Test Cases |    10 |
| Passed                      |    10 |
| Failed                      |     0 |
| Skipped                     |     0 |
| Blocked                     |     0 |

---

## 14. Conclusion

The StockFlow UI automation suite verifies the main business-critical flows of the system, including authentication, product management, inventory stock-in, inventory stock-out, stock-out validation, and role-based access control.

This automation suite can be reused for basic regression testing after future changes.