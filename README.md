# StockFlow – Mini Inventory Management System

StockFlow is a mini inventory management system built as a personal QA/Tester portfolio project. The system supports product management, stock-in transactions, stock-out transactions, dashboard summary, inventory report, transaction history, and role-based access control.

This repository is designed to demonstrate practical skills in full-stack development and testing documentation, including manual testing, API testing with Postman, database testing, and UI automation testing.

---

## 1. Tech Stack

### Backend

| Item | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| API | Spring Web |
| Database Access | Spring Data JPA |
| Security | Spring Security, JWT |
| Database | MySQL |
| Build Tool | Maven |
| API Documentation | Swagger/OpenAPI |
| Local Infrastructure | Docker Compose |

### Frontend

| Item | Technology |
|---|---|
| Library | React |
| Language | TypeScript |
| Build Tool | Vite |
| UI Library | Ant Design |
| API Client | Axios |
| Routing | React Router |

### Testing

| Area | Tool/Document |
|---|---|
| Manual Testing | Test scenarios, test cases, bug report, execution report |
| API Testing | Postman collection and environment |
| Database Testing | SQL validation checklist |
| UI Automation | Selenium Java, TestNG, Maven |

---

## 2. Project Structure

```text
stockflow-testing-project/
├── backend/                 # Spring Boot backend API
├── frontend/                # React TypeScript frontend
├── automation/              # Selenium UI automation tests
├── postman/                 # Postman collection and environment
├── testing/                 # Testing documents and reports
├── docker-compose.yml       # MySQL local database
├── .env.example             # Example environment variables
└── README.md
```

---

## 3. Main Features

| Module | Features |
|---|---|
| Authentication | Login with Admin/Staff account, JWT authentication |
| Dashboard | Total products, active/inactive products, total stock, low-stock, out-of-stock |
| Product Management | Create, view, update, delete, search, filter products |
| Stock-In | Create stock-in transaction and increase product quantity |
| Stock-Out | Create stock-out transaction and decrease product quantity |
| Transaction History | View stock-in and stock-out history |
| Inventory Report | View current quantity and stock status |
| Role-Based Access | Admin manages products; Staff can view products and create stock transactions |

---

## 4. Test Accounts

| Username | Password | Role |
|---|---|---|
| admin | 123456 | ADMIN |
| staff | 123456 | STAFF |

---

## 5. Local URLs

| Service | URL |
|---|---|
| Backend API | `http://localhost:8080` |
| Frontend | `http://localhost:3000` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |

---

## 6. How to Run Locally

### 6.1 Start MySQL

```bash
docker compose up -d
```

### 6.2 Run Backend

```bash
cd backend
mvn spring-boot:run
```

### 6.3 Run Frontend

```bash
cd frontend
npm install
npm run dev
```

### 6.4 Run Automation Tests

```bash
cd automation
mvn clean test
```

---

## 7. Postman API Testing

Postman files are located in:

```text
postman/
├── StockFlow_API_Collection.postman_collection.json
└── StockFlow_Local_Environment.postman_environment.json
```

### Import and Run

1. Import the collection file.
2. Import the environment file.
3. Select `StockFlow Local Environment` in Postman.
4. Make sure backend is running at `http://localhost:8080`.
5. Run requests in the recommended order from the Postman guide.

### API Test Execution Result

| Item | Result |
|---|---:|
| Total Requests | 36 |
| Passed | 36 |
| Failed | 0 |
| Blocked | 0 |
| Not Run | 0 |
| Status | Completed |

Detailed API execution summary:

```text
testing/06_api_testing/API_Test_Execution_Summary.md
```

---

## 8. Testing Documentation

| Folder | Description |
|---|---|
| `testing/00_requirement/` | Requirement summary and traceability |
| `testing/01_test_plan/` | Test plan |
| `testing/02_test_scenarios/` | Test scenarios and RTM |
| `testing/03_test_cases/` | Manual test cases and test data |
| `testing/04_bug_reports/` | Bug report template and sample reports |
| `testing/05_test_execution/` | Manual test execution report |
| `testing/06_api_testing/` | Postman guide and API execution summary |
| `testing/07_automation_testing/` | Automation testing plan and report |

---

## 9. Current Testing Status

| Area | Status |
|---|---|
| Requirement Review | Completed |
| Test Plan | Completed |
| Test Scenarios | Completed |
| Manual Test Cases | Completed |
| Bug Report Template | Completed |
| Manual Test Execution Report | Completed |
| API Testing with Postman | Completed |
| Database Testing Checklist | Completed |
| UI Automation Testing | Completed |

---

## 10. Suggested Git Commit

After updating the README and API testing result, commit with:

```bash
git add README.md frontend/README.md testing/README.md testing/06_api_testing/API_Test_Execution_Summary.md
git commit -m "docs: update project readme and api test result"
```
