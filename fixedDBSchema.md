# SaudiMart B2B E-Commerce Database Schema - FIXED VERSION

This SQL script defines the database schema for a B2B e-commerce platform, SaudiMart.
It includes tables for users, products, orders, contracts, inventory, and more, with appropriate
foreign key relationships and constraints.

## 1. Core Independent Tables (No foreign keys)

### Categories Table (self-referencing, but optional parent)

```sql
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    imageUrl TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id)
);

-- Users Table (Independent)
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    password_hash TEXT NOT NULL,
    role ENUM('BUYER','SELLER','ADMIN') NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_email_format CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$')
);

## 2. Dependent Tables (Depend on users)

-- Addresses Table (Depends on users)
CREATE TABLE addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    address_type ENUM('BILLING', 'SHIPPING') DEFAULT 'SHIPPING',
    company_name VARCHAR(100),
    street_address_1 VARCHAR(255) NOT NULL,
    street_address_2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Warehouses Table (Depends on users and addresses)
CREATE TABLE warehouses (
    warehouse_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE RESTRICT
);

## 3. Product Related Tables

-- Products Table (Depends on users and categories)
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id BIGINT,
    is_bulk_only BOOLEAN DEFAULT FALSE,
    minimum_order_quantity INT DEFAULT 1,
    weight DECIMAL(10,2),
    weight_unit VARCHAR(10) DEFAULT 'kg',
    dimensions VARCHAR(50), -- Format: LxWxH
    sku VARCHAR(50) UNIQUE,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

-- Product Variants Table (Depends on products) - NOW WITH BASE PRICE
CREATE TABLE product_variants (
    variant_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    variant_name VARCHAR(100),
    base_price DECIMAL(12,2) NOT NULL, -- FIXED: Each variant has its own base price
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    CONSTRAINT chk_base_price_positive CHECK (base_price > 0)
);

-- Product Images Table (Depends on products and variants)
CREATE TABLE product_images (
    image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    image_url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE
);

-- Product Specifications Table (Depends on products)
CREATE TABLE product_specifications (
    spec_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    spec_name VARCHAR(100) NOT NULL,
    spec_value TEXT NOT NULL,
    unit VARCHAR(50),
    display_order INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- FIXED: Price Tiers now work with variants instead of products
CREATE TABLE variant_price_tiers (
    tier_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    variant_id BIGINT NOT NULL,
    min_quantity INT NOT NULL,
    max_quantity INT,
    price_per_unit DECIMAL(12,2) NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE,
    CONSTRAINT chk_quantity_range CHECK (min_quantity > 0 AND (max_quantity IS NULL OR max_quantity >= min_quantity)),
    CONSTRAINT chk_price_positive CHECK (price_per_unit > 0),
    CONSTRAINT chk_discount_valid CHECK (discount_percent >= 0 AND discount_percent <= 100)
);

## 4. Inventory Management

-- Inventory Table (Depends on products, variants, warehouses)
CREATE TABLE inventory (
    inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    warehouse_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE,
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id) ON DELETE CASCADE,
    UNIQUE KEY unique_inventory (product_id, variant_id, warehouse_id),
    CONSTRAINT chk_inventory_positive CHECK (quantity >= 0 AND reserved_quantity >= 0),
    CONSTRAINT chk_reserved_not_exceed CHECK (reserved_quantity <= quantity)
);

## 5. Contract Management

-- Contracts Table (Depends on users)
CREATE TABLE contracts (
    contract_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    contract_number VARCHAR(50) UNIQUE DEFAULT (CONCAT('CNT-', UNIX_TIMESTAMP(), '-', CONNECTION_ID())),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    payment_terms INT DEFAULT 30,
    credit_limit DECIMAL(12,2),
    status ENUM('DRAFT', 'ACTIVE', 'EXPIRED', 'TERMINATED') DEFAULT 'DRAFT',
    terms_and_conditions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT chk_different_contract_parties CHECK (buyer_id != seller_id),
    CONSTRAINT chk_contract_dates CHECK (end_date > start_date),
    CONSTRAINT chk_credit_limit_positive CHECK (credit_limit IS NULL OR credit_limit > 0)
);

-- Contract Items Table (Depends on contracts, products, variants)
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
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE,
    CONSTRAINT chk_negotiated_price_positive CHECK (negotiated_price > 0),
    CONSTRAINT chk_contract_discount_valid CHECK (discount_percent >= 0 AND discount_percent <= 100),
    CONSTRAINT chk_commitment_quantities CHECK (min_commitment_quantity IS NULL OR min_commitment_quantity > 0),
    CONSTRAINT chk_max_quantity_valid CHECK (max_quantity IS NULL OR max_quantity >= COALESCE(min_commitment_quantity, 1))
);

## 6. Quotes Management

-- Quotes Table (Depends on users)
CREATE TABLE quotes (
    quote_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quote_number VARCHAR(50) UNIQUE DEFAULT (CONCAT('QUO-', UNIX_TIMESTAMP(), '-', CONNECTION_ID())),
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
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT chk_different_quote_parties CHECK (buyer_id != seller_id),
    CONSTRAINT chk_quote_validity CHECK (valid_until >= DATE(created_at)),
    CONSTRAINT chk_quote_amounts_positive CHECK (subtotal >= 0 AND tax_amount >= 0 AND total_amount >= 0)
);

-- Quote Items Table (Depends on quotes, products, variants)
CREATE TABLE quote_items (
    quote_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quote_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    quantity INT NOT NULL,
    quoted_price DECIMAL(12,2) NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    total_price DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (quote_id) REFERENCES quotes(quote_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE,
    CONSTRAINT chk_positive_quote_quantity CHECK (quantity > 0),
    CONSTRAINT chk_quoted_price_positive CHECK (quoted_price > 0),
    CONSTRAINT chk_quote_discount_valid CHECK (discount_percent >= 0 AND discount_percent <= 100),
    CONSTRAINT chk_quote_total_positive CHECK (total_price > 0)
);

## 7. Order Management

-- Orders Table (Depends on users, contracts, addresses)
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) UNIQUE DEFAULT (CONCAT('ORD-', UNIX_TIMESTAMP(), '-', CONNECTION_ID())),
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
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id) ON DELETE SET NULL,
    FOREIGN KEY (shipping_address_id) REFERENCES addresses(address_id) ON DELETE RESTRICT,
    FOREIGN KEY (billing_address_id) REFERENCES addresses(address_id) ON DELETE RESTRICT,
    CONSTRAINT chk_different_buyer_seller CHECK (buyer_id != seller_id),
    CONSTRAINT chk_order_amounts_positive CHECK (subtotal >= 0 AND tax_amount >= 0 AND shipping_cost >= 0 AND discount_amount >= 0 AND total_price >= 0),
    CONSTRAINT chk_delivery_dates CHECK (actual_delivery_date IS NULL OR expected_delivery_date IS NULL OR actual_delivery_date >= expected_delivery_date)
);

-- Order Items Table (Depends on orders, products, variants, warehouses)
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
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE RESTRICT,
    FOREIGN KEY (ship_from_warehouse_id) REFERENCES warehouses(warehouse_id) ON DELETE SET NULL,
    CONSTRAINT chk_positive_quantity CHECK (quantity > 0),
    CONSTRAINT chk_price_per_unit_positive CHECK (price_per_unit > 0),
    CONSTRAINT chk_valid_discount CHECK (discount_percent >= 0 AND discount_percent <= 100),
    CONSTRAINT chk_valid_tax CHECK (tax_percent >= 0 AND tax_percent <= 100),
    CONSTRAINT chk_order_item_total_positive CHECK (total_price > 0)
);

-- Order Approvals Table (Depends on orders, users)
CREATE TABLE order_approvals (
    approval_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    approver_id BIGINT NOT NULL,
    approval_level INT DEFAULT 1,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    comments TEXT,
    approval_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (approver_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT chk_approval_level_positive CHECK (approval_level > 0)
);

## 8. Payment Management

-- Payments Table (Depends on orders)
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
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT chk_payment_amount_positive CHECK (amount > 0)
);

## 9. Credit Management

-- Credit Applications Table (Depends on users)
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
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES users(user_id) ON DELETE SET NULL,
    CONSTRAINT chk_different_credit_parties CHECK (buyer_id != seller_id),
    CONSTRAINT chk_reasonable_credit_limit CHECK (requested_limit > 0 AND requested_limit <= 10000000),
    CONSTRAINT chk_approved_limit_reasonable CHECK (approved_limit IS NULL OR (approved_limit > 0 AND approved_limit <= requested_limit))
);
```

## 10. Indexes for Performance

    -- Users table indexes
    CREATE INDEX idx_users_email ON users(email);
    CREATE INDEX idx_users_role ON users(role);

    -- Address indexes
    CREATE INDEX idx_addresses_user_id ON addresses(user_id);
    CREATE UNIQUE INDEX idx_unique_default_address ON addresses(user_id, address_type, is_default) WHERE is_default = TRUE;

    -- Product indexes
    CREATE INDEX idx_products_seller_id ON products(seller_id);
    CREATE INDEX idx_products_category_id ON products(category_id);
    CREATE INDEX idx_products_sku ON products(sku);
    CREATE INDEX idx_products_available ON products(available);

    -- Variant indexes
    CREATE INDEX idx_variants_product_id ON product_variants(product_id);
    CREATE INDEX idx_variants_sku ON product_variants(sku);

    -- Inventory indexes
    CREATE INDEX idx_inventory_product_warehouse ON inventory(product_id, warehouse_id);
    CREATE INDEX idx_inventory_warehouse_id ON inventory(warehouse_id);

    -- Order indexes
    CREATE INDEX idx_orders_buyer_seller ON orders(buyer_id, seller_id);
    CREATE INDEX idx_orders_status ON orders(status);
    CREATE INDEX idx_orders_created_at ON orders(created_at);
    CREATE INDEX idx_order_items_order_id ON order_items(order_id);
    CREATE INDEX idx_order_items_product_id ON order_items(product_id);

    -- Contract indexes
    CREATE INDEX idx_contracts_buyer_seller ON contracts(buyer_id, seller_id);
    CREATE INDEX idx_contracts_status ON contracts(status);

    -- Quote indexes
    CREATE INDEX idx_quotes_buyer_seller ON quotes(buyer_id, seller_id);
    CREATE INDEX idx_quotes_status ON quotes(status);

    -- Payment indexes
    CREATE INDEX idx_payments_order_id ON payments(order_id);
    CREATE INDEX idx_payments_status ON payments(payment_status);

    ---
    ## 11. Triggers for Automatic Calculations

    -- Trigger to update order totals when order items change
    DELIMITER //
    CREATE TRIGGER update_order_totals_insert
        AFTER INSERT ON order_items
        FOR EACH ROW
    BEGIN
        UPDATE orders 
        SET subtotal = (
            SELECT SUM(total_price) 
            FROM order_items 
            WHERE order_id = NEW.order_id
        ),
        total_price = subtotal + tax_amount + shipping_cost - discount_amount
        WHERE order_id = NEW.order_id;
    END//

    CREATE TRIGGER update_order_totals_update
        AFTER UPDATE ON order_items
        FOR EACH ROW
    BEGIN
        UPDATE orders 
        SET subtotal = (
            SELECT SUM(total_price) 
            FROM order_items 
            WHERE order_id = NEW.order_id
        ),
        total_price = subtotal + tax_amount + shipping_cost - discount_amount
        WHERE order_id = NEW.order_id;
    END//

    CREATE TRIGGER update_order_totals_delete
        AFTER DELETE ON order_items
        FOR EACH ROW
    BEGIN
        UPDATE orders 
        SET subtotal = (
            SELECT COALESCE(SUM(total_price), 0) 
            FROM order_items 
            WHERE order_id = OLD.order_id
        ),
        total_price = subtotal + tax_amount + shipping_cost - discount_amount
        WHERE order_id = OLD.order_id;
    END//
    DELIMITER ;