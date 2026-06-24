# StockFlow Frontend

This folder contains the React frontend for **StockFlow – Mini Inventory Management System**.

The frontend provides the user interface for login, dashboard, product management, stock-in, stock-out, transaction history, and inventory report.

---

## 1. Technology Stack

| Item | Technology |
|---|---|
| Library | React |
| Language | TypeScript |
| Build Tool | Vite |
| UI Library | Ant Design |
| Routing | React Router |
| API Client | Axios |
| Date Utility | Day.js |

---

## 2. Main Pages

| Page | Route | Description |
|---|---|---|
| Login | `/login` | Login with Admin or Staff account |
| Dashboard | `/dashboard` | View inventory summary cards |
| Product List | `/products` | View, search, filter, edit, and delete products |
| Create Product | `/products/create` | Admin creates a new product |
| Edit Product | `/products/edit/:id` | Admin updates product information |
| Stock-In | `/stock-in/create` | Admin/Staff creates stock-in transaction |
| Stock-Out | `/stock-out/create` | Admin/Staff creates stock-out transaction |
| Stock-In History | `/stock-in/history` | View stock-in history |
| Stock-Out History | `/stock-out/history` | View stock-out history |
| Inventory Report | `/inventory/report` | View inventory report and stock status |

---

## 3. Role-Based UI Behavior

| Role | Permission |
|---|---|
| Admin | Can create, update, delete, and view products; can create stock-in/out; can view reports |
| Staff | Can view products; can create stock-in/out; can view reports; cannot create/update/delete products |

---

## 4. Local Configuration

The frontend connects to the backend API at:

```text
http://localhost:8080
```

Frontend runs locally at:

```text
http://localhost:3000
```

---

## 5. How to Run

Open terminal in the frontend folder:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run development server:

```bash
npm run dev
```

Build production bundle:

```bash
npm run build
```

Preview production build:

```bash
npm run preview
```

---

## 6. Test Accounts

| Username | Password | Role |
|---|---|---|
| admin | 123456 | ADMIN |
| staff | 123456 | STAFF |

---

## 7. Notes for Testing

Before testing the frontend, make sure the following services are running:

1. MySQL database
2. StockFlow backend at `http://localhost:8080`
3. StockFlow frontend at `http://localhost:3000`

Recommended frontend smoke test flow:

1. Login as Admin.
2. View dashboard.
3. Create a product.
4. Search product by name.
5. Create stock-in transaction.
6. Create stock-out transaction.
7. View inventory report.
8. Login as Staff and verify product create/update/delete actions are not available.
