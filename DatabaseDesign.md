# Complete B2B E-Commerce Database Schema Design

### 1. Core Reference Tables 

#### Categories Table
```sql
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id)
);
```

#### Addresses Table 
```sql
CREATE TABLE addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    address_type ENUM('BILLING', 'SHIPPING', 'WAREHOUSE') DEFAULT 'SHIPPING',
    company_name VARCHAR(100),
    street_address_1 VARCHAR(255) NOT NULL,
    street_address_2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

### 2. User Management 

#### Users Table
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    password_hash TEXT NOT NULL,
    role ENUM('BUYER','SELLER','ADMIN') NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Product Management 

#### Products Table
```sql
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id BIGINT,
    base_price DECIMAL(12,2), -- ONLY base price here
    is_bulk_only BOOLEAN DEFAULT FALSE,
    minimum_order_quantity INT DEFAULT 1,
    weight DECIMAL(10,2),
    weight_unit VARCHAR(10) DEFAULT 'kg',
    dimensions VARCHAR(50), -- Format: LxWxH
    sku VARCHAR(50) UNIQUE,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
```

#### Product Variants Table
```sql
CREATE TABLE product_variants (
    variant_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    variant_name VARCHAR(100),
    additional_price DECIMAL(10,2) DEFAULT 0, -- ONLY additional price to base
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

#### Product Images Table (FIXED - Frontend Friendly)
**PROBLEM SOLVED:** Choose ONE approach for images
```sql
CREATE TABLE product_images (
    image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL, -- Always link to product
    variant_id BIGINT NULL, -- Optional: only if image is variant-specific
    image_url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id)
);
```

#### Product Specifications Table 
```sql
CREATE TABLE product_specifications (
    spec_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    spec_name VARCHAR(100) NOT NULL,
    spec_value TEXT NOT NULL,
    unit VARCHAR(50),
    display_order INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

#### Price Tiers Table
```sql
CREATE TABLE price_tiers (
    tier_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    min_quantity INT NOT NULL,
    max_quantity INT,
    price_per_unit DECIMAL(12,2) NOT NULL, -- ONLY for bulk pricing
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

### 4. Inventory Management
#### Warehouses Table 
```sql
CREATE TABLE warehouses (
    warehouse_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);
```

#### Inventory Table
```sql
CREATE TABLE inventory (
    inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    variant_id BIGINT NULL, -- NULL = base product, NOT NULL = specific variant
    warehouse_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id),
    UNIQUE KEY unique_inventory (product_id, variant_id, warehouse_id)
);
```

### 5. Contract Management 

#### Contracts Table
```sql
CREATE TABLE contracts (
    contract_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    contract_number VARCHAR(50) UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    payment_terms INT DEFAULT 30,
    credit_limit DECIMAL(12,2),
    status ENUM('DRAFT', 'ACTIVE', 'EXPIRED', 'TERMINATED') DEFAULT 'DRAFT',
    terms_and_conditions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (seller_id) REFERENCES users(user_id)
);
```

#### Contract Items Table 
```sql
CREATE TABLE contract_items (
    contract_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    contract_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    negotiated_price DECIMAL(12,2) NOT NULL,
    min_commitment_quantity INT,
    max_quantity INT,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id)
);
```

### 6. Order Management 

#### Orders Table
```sql
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) UNIQUE,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    contract_id BIGINT,
    shipping_address_id BIGINT NOT NULL,
    billing_address_id BIGINT NOT NULL,
    payment_method ENUM('CASH', 'BANK_TRANSFER', 'CREDIT', 'CARD') DEFAULT 'BANK_TRANSFER',
    reference_number VARCHAR(50),
    purchase_order_number VARCHAR(50),
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    notes TEXT,
    status ENUM('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'REJECTED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'DRAFT',
    subtotal DECIMAL(12,2) DEFAULT 0,
    tax_amount DECIMAL(12,2) DEFAULT 0,
    shipping_cost DECIMAL(12,2) DEFAULT 0,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    total_price DECIMAL(12,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id),
    FOREIGN KEY (shipping_address_id) REFERENCES addresses(address_id),
    FOREIGN KEY (billing_address_id) REFERENCES addresses(address_id)
);
```

#### Order Items Table 
```sql
CREATE TABLE order_items (
    order_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(12,2) NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    tax_percent DECIMAL(5,2) DEFAULT 0,
    total_price DECIMAL(12,2) NOT NULL,
    ship_from_warehouse_id BIGINT,
    status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    notes TEXT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id),
    FOREIGN KEY (ship_from_warehouse_id) REFERENCES warehouses(warehouse_id)
);
```

#### Order Approvals Table 
```sql
CREATE TABLE order_approvals (
    approval_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    approver_id BIGINT NOT NULL,
    approval_level INT DEFAULT 1,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    comments TEXT,
    approval_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (approver_id) REFERENCES users(user_id)
);
```

### 7. Payment Management 

#### Payments Table
```sql
CREATE TABLE payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    payment_reference VARCHAR(100) UNIQUE,
    amount DECIMAL(12,2) NOT NULL,
    payment_type ENUM('CASH', 'BANK_TRANSFER', 'CREDIT', 'CARD') NOT NULL,
    transaction_id VARCHAR(100),
    payment_status ENUM('PENDING', 'SUCCESS', 'FAILED', 'REFUNDED', 'CANCELLED') DEFAULT 'PENDING',
    payment_date TIMESTAMP,
    due_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
```

### 8. Additional B2B Features 

#### Credit Applications Table
```sql
CREATE TABLE credit_applications (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    requested_limit DECIMAL(12,2) NOT NULL,
    approved_limit DECIMAL(12,2),
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'EXPIRED') DEFAULT 'PENDING',
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    review_date TIMESTAMP,
    expiry_date DATE,
    reviewer_id BIGINT,
    notes TEXT,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (reviewer_id) REFERENCES users(user_id)
);
```

#### Quotes Table
```sql
CREATE TABLE quotes (
    quote_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quote_number VARCHAR(50) UNIQUE,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    status ENUM('DRAFT', 'SENT', 'ACCEPTED', 'REJECTED', 'EXPIRED') DEFAULT 'DRAFT',
    valid_until DATE NOT NULL,
    subtotal DECIMAL(12,2) DEFAULT 0,
    tax_amount DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (seller_id) REFERENCES users(user_id)
);
```

#### Quote Items Table
```sql
CREATE TABLE quote_items (
    quote_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quote_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    quantity INT NOT NULL,
    quoted_price DECIMAL(12,2) NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    total_price DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (quote_id) REFERENCES quotes(quote_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id)
);
```

---
