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
