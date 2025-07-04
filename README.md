# SaudiMart B2B E-Commerce Platform

[![Build and Deploy SaudiMart Services](https://github.com/sayeedajmal/SaudiMart/actions/workflows/main.yml/badge.svg)](https://github.com/sayeedajmal/SaudiMart/actions/workflows/main.yml)

This repository contains the system design and implementation details for the SaudiMart B2B e-commerce platform.

## What is SaudiMart?

SaudiMart is a B2B marketplace platform that connects buyers and sellers. It facilitates wholesale transactions with features designed for business-to-business interactions.

## Core Features

| Feature                 | Description                                                                       |
| ----------------------- | --------------------------------------------------------------------------------- |
| **User Authentication** | Login/Signup with roles (BUYER, SELLER, ADMIN).                                   |
| **Product Listings**    | Sellers post product details (images, description, price, MOQ, etc.).             |
| **Search & Filter**     | Buyers search for products/sellers using filters (location, price, rating, etc.). |
| **Quotes**              | Buyers can request quotes from sellers for bulk orders.                           |
| **Contracts**           | Buyers and Sellers can establish long-term supply contracts.                      |
| **Order Management**    | Creation, tracking, and management of orders.                                     |
| **Inventory Management**| Sellers manage product quantities in different warehouses.                          |
| **Credit Management**   | Handling credit applications and limits for buyers.                               |
| **Admin Panel**         | Admin functionalities for platform oversight and management.                      |
| **Notifications**       | Real-time updates and alerts.                                                     |

## Technology Stack

- **Backend:** Java, Spring Boot, Spring Cloud (Eureka, Gateway)
- **Containerization:** Docker
- **Messaging:** Kafka
- **Frontend:** React (or similar)
- **Database:** MySQL/PostgreSQL
- **Cloud:** AWS (or similar cloud-ready design)

## Microservices Architecture

| Service              | Responsibility                                         |
| -------------------- | ------------------------------------------------------ |
| `auth-service`       | Handles user authentication and authorization.         |
| `product-service`    | Manages products, categories, inventory, and related data. |
| `order-service`      | Manages orders, order items, and order approvals.      |
| `contract-service`   | Manages contracts and contract items.                  |
| `quote-service`      | Manages quotes and quote items.                        |
| `inventory-service`  | Dedicated service for complex inventory operations.     |
| `credit-service`     | Manages credit applications and limits.                |
| `gateway-service`    | API Gateway for routing and cross-cutting concerns.    |
| `eureka-server`      | Service discovery.                                     |
| `notification-service`| Handles system notifications (potentially via Kafka). |
| `search-service`     | Provides search and filtering capabilities.             |
| `media-service`      | Stores and manages product images and other media.     |

## Database Schema Creation Roadmap

The database schema is defined in `fixedDBSchema.md`. The tables should be created in phases based on their dependencies:

**Phase 1: Core Independent Tables**
- `categories`
- `users`

**Phase 2: Dependent Tables (Depend on Users)**
- `addresses`
- `warehouses` (depends on `users` and `addresses`)

**Phase 3: Product Related Tables**
- `products` (depends on `users` and `categories`)
- `product_variants` (depends on `products`)
- `product_images` (depends on `products` and `product_variants`)
- `product_specifications` (depends on `products`)
- `variant_price_tiers` (depends on `product_variants`)

**Phase 4: Inventory Management**
- `inventory` (depends on `products`, `product_variants`, `warehouses`)

**Phase 5: Contract Management**
- `contracts` (depends on `users`)
- `contract_items` (depends on `contracts`, `products`, `product_variants`)

**Phase 6: Quotes Management**
- `quotes` (depends on `users`)
- `quote_items` (depends on `quotes`, `products`, `product_variants`)

**Phase 7: Order Management**
- `orders` (depends on `users`, `contracts`, `addresses`)
- `order_items` (depends on `orders`, `products`, `product_variants`, `warehouses`)
- `order_approvals` (depends on `orders`, `users`)

**Phase 8: Payment Management**
- `payments` (depends on `orders`)

**Phase 9: Credit Management**
- `credit_applications` (depends on `users`)

**Phase 10: Indexes and Triggers**
- Apply indexes and triggers for performance and data integrity.

## Table Creation Permissions (Typical)

| Table Group            | Responsible Role(s) (Typically) | Notes                                       |
| ---------------------- | ------------------------------- | ------------------------------------------- |
| `categories`           | ADMIN                           | Platform-wide categories.                 |
| `users`                | BUYER, SELLER, ADMIN            | Self-registration, Admin can manage.      |
| `addresses`            | BUYER, SELLER                   | Users manage their own addresses.           |
| `warehouses`           | SELLER                          | Sellers manage their own warehouses.        |
| Product Related        | SELLER                          | Sellers create and manage their products.   |
| `inventory`            | SELLER                          | Sellers manage their product inventory.     |
| `contracts`            | BUYER, SELLER                   | Contracts are negotiated between parties. |
| `contract_items`       | BUYER, SELLER                   | Part of contract negotiation.               |
| `quotes`               | BUYER, SELLER                   | Quotes are exchanged between parties.       |
| `quote_items`          | BUYER, SELLER                   | Part of quote creation.                     |
| `orders`               | BUYER, SELLER                   | Orders initiated by buyers, processed by sellers. |
| `order_items`          | BUYER, SELLER                   | Part of order creation.                     |
| `order_approvals`      | ADMIN, potentially SELLER/BUYER | Depends on approval workflow.             |
| `payments`             | BUYER (initiation), System      | Payments processed by the system.         |
| `credit_applications`  | BUYER (initiation), ADMIN/SELLER | Buyers apply, Admin/Seller review.       |

## Getting Started

Further instructions on setting up the development environment and running the services will be provided in dedicated documentation.
# saudiMART System Design

This repository contains a system design for a B2B marketplace platform like saudiMART.

## What is saudiMART?

saudiMART is a B2B marketplace platform that connects buyers and suppliers. It includes features like:

- Product listings
- Seller/buyer registration & authentication
- Search & filter
- Quote request
- Chat/communication
- Order management

## Core Features Breakdown

| Feature                 | Description                                                                       |
| ----------------------- | --------------------------------------------------------------------------------- |
| **User Authentication** | Login/Signup with roles (Buyer/Seller/Admin).                                     |
| **Product Listings**    | Sellers post product details (images, description, price, MOQ, etc.).             |
| **Search & Filter**     | Buyers search for products/sellers using filters (location, price, rating, etc.). |
| **Leads/Enquiry**       | Buyers request quotes or send enquiries to sellers.                               |
| **Chat System**         | Messaging between buyer and seller.                                               |
| **Admin Panel**         | Admin to monitor listings, users, reports.                                        |
| **Notifications**       | Email/SMS/Push alerts on activity.                                                |
| **Payments (optional)** | B2B doesn't usually require payment gateway, but some platforms support it.       |

## Your Tech Stack Alignment

- **Java**, **Spring Boot**
- **Microservices** with **Eureka** + **Gateway**
- **Docker** & **Kafka**
- **Frontend** (likely **React** or similar)
- **MySQL/PostgreSQL**
- **AWS** or cloud-ready design

## System Design - Microservices Architecture

| Service              | Responsibility                                     |
| -------------------- | -------------------------------------------------- |
| auth-service         | Handles user login/signup/roles                    |
| product-service      | CRUD for product listings                          |
| search-service       | Search & filtering (use ElasticSearch for scaling) |
| enquiry-service      | Handles quote requests, buyer-seller connections   |
| chat-service         | Real-time communication (WebSocket + Kafka)        |
| notification-service | Email/SMS/push notifications                       |
| admin-service        | Analytics, user/report control                     |
| api-gateway          | Routing via Spring Cloud Gateway                   |
| discovery-service    | Eureka or Consul for service discovery             |
| media-service        | Store product images/videos (AWS S3 or similar)    |

## External Integrations

- ElasticSearch → Search & filtering
- Kafka → For chat, notifications, system events
- Redis → Caching & session/token management
- S3 or Cloudinary → For storing media
- Twilio/SendGrid → For notifications
