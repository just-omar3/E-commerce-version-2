# 🚀 Full-Stack E-Commerce Platform

A production-oriented e-commerce platform built with **Spring Boot **, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **Microsoft SQL Server**.

The project demonstrates modern backend development practices including authentication, authorization, transaction management, optimistic locking, role-based access control, REST API design, and database modeling.

A lightweight frontend is included to interact with the API and demonstrate end-to-end functionality.

---

# 📌 Overview

This application supports:

* Customer registration and authentication
* Product catalog management
* Shopping cart and checkout workflow
* Order processing
* Inventory management
* User administration
* Employee and administrator dashboards
* Role and permission management

The system is designed using a layered architecture with clear separation of concerns between controllers, services, repositories, and persistence.

---

# ✨ Key Features

## 🔐 Authentication & Security

* JWT-based stateless authentication
* BCrypt password hashing
* Spring Security integration
* Role-Based Access Control (RBAC)
* Permission-based authorization
* Method-level security using `@PreAuthorize`
* Ownership validation for protected resources
* Secure API endpoints

---

## 👤 User Management

* User registration and login
* Customer profile creation
* Employee profile support
* Admin user management
* Role assignment
* Current user endpoint (`/users/me`)

---

## 📦 Product Management

* Create, update, delete products
* Public product catalog
* Product search and filtering
* Inventory management
* Stock updates
* Optimistic locking using JPA `@Version`

---

## 🛒 Order Management

* Place orders
* Order history
* Order details and order items
* Automatic stock deduction
* Transactional order processing
* Ownership validation for customer orders

---

## 👥 Roles & Permissions

Supported roles include:

* SUPER_ADMIN
* ADMIN
* PRODUCT_MANAGER
* ORDER_MANAGER
* CUSTOMER_SUPPORT
* CUSTOMER

Permissions are assigned through a flexible role-permission model.

---

# 🏗️ Architecture

```text
Client (Frontend / Postman)
            │
            ▼
Spring Security Filter Chain
            │
            ▼
JWT Authentication Filter
            │
            ▼
Controllers
            │
            ▼
Services
            │
            ▼
Repositories
            │
            ▼
SQL Server Database
```

### Architectural Patterns

* Layered Architecture
* Repository Pattern
* DTO Pattern
* Dependency Injection
* Service Layer Pattern
* RESTful API Design

---

# 🔒 Security Architecture

Authentication flow:

```text
User Login
    │
    ▼
AuthenticationManager
    │
    ▼
JWT Generation
    │
    ▼
Client Stores Token
    │
    ▼
Authorization: Bearer <token>
    │
    ▼
JWT Filter Validation
    │
    ▼
Security Context
    │
    ▼
Protected Endpoint Access
```

Security features include:

* Stateless authentication
* JWT token validation
* Password encryption using BCrypt
* Role-based endpoint protection
* Permission-based authorization
* Resource ownership validation

---

# 🗄️ Database Design

## Main Entities

```text
Person
   │
   ▼
Users
   │
   ├── Customer
   │
   ├── Employee
   │
   └── Role
          │
          ▼
      Permission

Users
   │
   ▼
Orders
   │
   ▼
OrderItems
   │
   ▼
Product
```

Key relationships:

* User ↔ Person (1:1)
* User ↔ Role (Many:1)
* Role ↔ Permission (Many)
* User ↔ Orders (1)
* Order ↔ OrderItems (1)
* Product ↔ OrderItems (1)

---

# ⚡ Business Logic Highlights

### Transactional Order Processing

Order creation is executed within a transaction:

* Validate stock availability
* Calculate totals
* Create order
* Create order items
* Update inventory
* Commit transaction

This prevents partial order creation and maintains consistency.

### Optimistic Locking

Products use JPA optimistic locking:

```java
@Version
private Long version;
```

This prevents concurrent inventory updates from overwriting each other.

### Ownership Validation

Customers can only access their own orders.

Implemented using Spring Security expressions and service-layer ownership checks.

---

# 🛠️ Technology Stack

| Category            | Technology            |
| ------------------- | --------------------- |
| Language            | Java 26               |
| Framework           | Spring Boot           |
| Security            | Spring Security       |
| Authentication      | JWT                   |
| Password Encryption | BCrypt                |
| ORM                 | Spring Data JPA       |
| Persistence         | Hibernate             |
| Database            | Microsoft SQL Server  |
| Validation          | Jakarta Validation    |
| Build Tool          | Maven                 |
| Frontend            | HTML, CSS, JavaScript |
| Version Control     | Git                   |
| API Testing         | HTTP Client           |

---

# 📂 Project Structure

```text
src/main/java
│
├── config
├── security
├── auth
├── user
├── person
├── role
├── permission
├── product
├── order
├── customer
├── employee
├── exception
└── EcommerceApplication
```

---

# 🔌 Main API Endpoints

## Authentication

```http
POST /auth/register
POST /auth/login
```

## Products

```http
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
PATCH  /products/{id}/stock
```

## Orders

```http
GET  /orders
POST /orders
GET  /orders/user/{userId}
GET  /orders/{orderId}/items
```

## Users

```http
GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}
GET    /users/me
```

---

# 🚀 Running the Application

## Prerequisites

* Java 17+
* SQL Server
* Maven

## Configure Database

Update:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Ecommerce
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Configure JWT

```properties
jwt.secret=your-secret-key
jwt.expirationMs=86400000
```

## Run

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---


# 🎯 What This Project Demonstrates

This project showcases:

* Backend API development with Spring Boot
* Authentication and authorization
* RESTful API design
* Database modeling
* Transaction management
* Concurrency handling
* Secure application development
* Layered architecture
* Enterprise application structure

---

# 🔮 Future Improvements

* Refresh token support
* Swagger/OpenAPI documentation
* Docker containerization
* Email verification
* Audit logging
* Advanced analytics
* Integration testing

---

#
