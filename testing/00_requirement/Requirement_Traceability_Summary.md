# StockFlow Requirement Traceability Summary

## 1. Purpose

This document maps StockFlow requirements and business rules to the related testing areas.

The purpose of this traceability summary is to ensure that important requirements are covered by test scenarios, manual test cases, API testing, and database testing.

## 2. Functional Requirement Traceability

| Requirement ID | Module | Requirement Summary | Related Test Area | Coverage Status |
|---|---|---|---|---|
| FR-AUTH-001 | Authentication | User can log in using username and password | Manual Test, API Test, UI Test | Planned |
| FR-AUTH-002 | Authentication | User can log out | Manual Test, UI Test | Planned |
| FR-DASH-001 | Dashboard | User can view dashboard summary after login | Manual Test, API Test, UI Test | Planned |
| FR-PROD-001 | Product Management | User can view product list | Manual Test, API Test, UI Test | Planned |
| FR-PROD-002 | Product Management | Admin can create product | Manual Test, API Test, DB Test, Role Test | Planned |
| FR-PROD-003 | Product Management | Admin can update product information | Manual Test, API Test, DB Test, Role Test | Planned |
| FR-PROD-004 | Product Management | Admin can delete product based on business rules | Manual Test, API Test, DB Test, Role Test | Planned |
| FR-PROD-005 | Product Management | Admin can change product status | Manual Test, API Test, DB Test, Role Test | Planned |
| FR-STIN-001 | Stock-In | Admin and Staff can create stock-in transactions | Manual Test, API Test, DB Test | Planned |
| FR-STIN-002 | Stock-In | User can view stock-in history | Manual Test, API Test, UI Test | Planned |
| FR-STOUT-001 | Stock-Out | Admin and Staff can create stock-out transactions | Manual Test, API Test, DB Test | Planned |
| FR-STOUT-002 | Stock-Out | User can view stock-out history | Manual Test, API Test, UI Test | Planned |
| FR-REP-001 | Inventory Report | User can view inventory report | Manual Test, API Test, DB Test, UI Test | Planned |

## 3. Non-Functional Requirement Traceability

| Requirement ID | Category | Requirement Summary | Related Test Area | Coverage Status |
|---|---|---|---|---|
| NFR-USE-001 | Usability | UI should be simple and easy to use | UI Test | Planned |
| NFR-USE-002 | Usability | Validation messages should be clear | Manual Test, UI Test, Validation Test | Planned |
| NFR-PER-001 | Performance | Product list should load within 3 seconds for 1,000 records | Performance Check | Optional |
| NFR-PER-002 | Performance | Search and filter should return results within 3 seconds | Performance Check | Optional |
| NFR-SEC-001 | Security | Users must log in before accessing the system | Manual Test, API Test, Security Test | Planned |
| NFR-SEC-002 | Security | Passwords must not be stored as plain text | Database Test, Security Check | Planned |
| NFR-SEC-003 | Security | Staff users must not access Admin-only actions | Manual Test, API Test, Role Test | Planned |
| NFR-SEC-004 | Security | Protected APIs must require authentication | API Test, Security Test | Planned |
| NFR-DATA-001 | Data Integrity | Product quantity must be accurate after stock-in and stock-out | Manual Test, API Test, DB Test | Planned |
| NFR-DATA-002 | Data Integrity | Stock-out must never make product quantity negative | Manual Test, API Test, DB Test | Planned |
| NFR-DATA-003 | Data Integrity | Transaction history must not be deleted when product status changes | Manual Test, DB Test | Planned |

## 4. Business Rule Traceability

| Rule ID | Business Rule | Related Test Area | Coverage Status |
|---|---|---|---|
| BR-001 | Product name must be unique | Manual Test, API Test, DB Test | Planned |
| BR-002 | Product price must be greater than 0 | Manual Test, API Test, Validation Test | Planned |
| BR-003 | Product quantity cannot be negative | Manual Test, API Test, DB Test | Planned |
| BR-004 | Product quantity can only be changed through stock-in or stock-out | Manual Test, API Test, DB Test | Planned |
| BR-005 | Stock-in quantity must be greater than 0 | Manual Test, API Test, Validation Test | Planned |
| BR-006 | Stock-out quantity must be greater than 0 | Manual Test, API Test, Validation Test | Planned |
| BR-007 | Stock-out quantity cannot exceed current inventory quantity | Manual Test, API Test, DB Test | Planned |
| BR-008 | Inactive products cannot be used in new stock-in or stock-out transactions | Manual Test, API Test, DB Test | Planned |
| BR-009 | Products with quantity greater than 0 cannot be deleted | Manual Test, API Test, DB Test | Planned |
| BR-010 | Products with transaction history cannot be deleted | Manual Test, API Test, DB Test | Planned |
| BR-011 | Low stock means quantity is from 1 to 10 | Manual Test, API Test, DB Test | Planned |
| BR-012 | Out of stock means quantity equals 0 | Manual Test, API Test, DB Test | Planned |
| BR-013 | Staff cannot create, update, or delete products | Manual Test, API Test, Role Test | Planned |
| BR-014 | User cannot access protected pages without login | Manual Test, API Test, Security Test | Planned |
| BR-015 | User cannot access Admin-only pages as Staff | Manual Test, API Test, Role Test | Planned |

## 5. Module Coverage Summary

| Module | Requirement Coverage | Manual Test | API Test | Database Test | UI Test |
|---|---|---|---|---|---|
| Authentication | Covered | Planned | Planned | Not Required | Planned |
| Dashboard | Covered | Planned | Planned | Planned | Planned |
| Product Management | Covered | Planned | Planned | Planned | Planned |
| Stock-In | Covered | Planned | Planned | Planned | Planned |
| Stock-Out | Covered | Planned | Planned | Planned | Planned |
| Transaction History | Covered | Planned | Planned | Planned | Planned |
| Inventory Report | Covered | Planned | Planned | Planned | Planned |
| Role-Based Access | Covered | Planned | Planned | Not Required | Planned |

## 6. Traceability Notes

- High priority requirements should be covered by manual test cases and API test cases.
- Business rules related to product quantity should also be covered by database validation.
- Role-based access rules should be tested from both UI and API levels.
- Performance requirements are marked as optional because this project is mainly focused on manual testing, API testing, and database testing for portfolio purposes.
- Automation coverage will be added later for stable and important flows.