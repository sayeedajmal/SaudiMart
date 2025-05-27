# SaudiMART Security Considerations

This document outlines the security considerations and implementation details for the saudiMART B2B marketplace platform.

## Authentication Strategy (JWT)

The platform employs a JSON Web Token (JWT) based authentication strategy. Upon successful login via the `MartAuth` service, a user is issued a JWT. This token is then used by the client to authenticate subsequent requests to other microservices. JWTs are stateless, making them suitable for a microservices architecture.

## API Gateway Authentication Handling

A dedicated API Gateway (not explicitly shown as a separate module in the current project structure, but assumed for a production setup) serves as the entry point for all external requests. The gateway is responsible for:

1.  **Receiving Requests:** Intercepting all incoming API requests.
2.  **Routing Authentication Requests:** Directing login and signup requests to the `MartAuth` service.
3.  **JWT Validation:** For all other requests (to downstream services like `MartProduct`), the gateway validates the JWT provided in the `Authorization: Bearer <token>` header.
4.  **User Information Propagation:** Upon successful JWT validation, the gateway extracts key user information, such as the `user_id` and `role` (from the `users` table in the database, as defined in `DatabaseDesign.md`), and injects this information into the request headers (e.g., `X-User-Id`, `X-User-Roles`) before forwarding the request to the appropriate downstream microservice.

## Authorization with User Roles

User roles are defined in the `users` table of the database (`role` column) and can be 'BUYER', 'SELLER', or 'ADMIN'. These roles are crucial for authorization:

*   **MartAuth Module:** The `MartAuth` module uses the roles extracted from the JWT (validated by its internal `JwtRequestFilter`) to enforce method-level authorization, particularly in the `UserController`, using `@PreAuthorize` annotations to restrict access to specific user management operations based on roles (e.g., only ADMINs can manage user accounts).
*   **Downstream Services (e.g., MartProduct):** Microservices like `MartProduct` rely on the user information (specifically the roles) passed by the API Gateway in the request headers. The `GatewayAuthFilter` in these services reads these headers and sets the security context. This allows the downstream services to perform their own authorization checks based on the user's role. For example, in the `MartProduct` service, authorization logic would ensure that only sellers can create or update products (linked via the `seller_id` in the `products` table), while buyers can only view product listings.

## Sensitive Data Handling and Data Separation

*   **Password Hashing:** User passwords are not stored in plain text. The `MartAuth` service uses a strong one-way hashing algorithm (BCrypt, as configured in `SecurityConfig`) to store password hashes in the `password_hash` column of the `users` table.
*   **Data Separation by Role:** The database schema (`DatabaseDesign.md`) incorporates mechanisms for data separation based on roles, particularly for sellers. For example, the `products` table includes a `seller_id` foreign key referencing the `users` table. This allows the `MartProduct` service to easily filter and retrieve only the products belonging to the authenticated seller, preventing sellers from accessing or modifying products owned by others. Similar patterns are expected for other seller-specific data (e.g., `warehouses`, `inventory`, `contracts` where the seller is involved).

## Security Flow Diagram (Text-Based)

+-----------+      +----------------+      +-----------+
|  Client   | <--> |  API Gateway   | <--> | MartAuth  |
+-----------+      +----------------+      +-----------+
      ^                    |
      |                    | (Validated JWT,
      |                    |  User Info Headers)
      |                    |
      |              +------------+
      |              | Downstream |
      +------------> | Service    | (e.g., MartProduct)
                     +------------+

Security Flow:

1. Client sends Login/Signup request to API Gateway.
2. API Gateway routes request to MartAuth service.
3. MartAuth authenticates user and issues JWT.
4. Client includes JWT in subsequent requests to API Gateway for other services.
5. API Gateway intercepts request, validates JWT, extracts user info (ID, roles).
6. API Gateway adds user info as headers (X-User-Id, X-User-Roles) to the request.
7. API Gateway routes request with headers to the appropriate Downstream Service (e.g., MartProduct).
8. Downstream Service's GatewayAuthFilter reads user info from headers.
9. Downstream Service performs authorization based on user roles.
