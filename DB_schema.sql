-- SQL schema for Saudi Mart Product service based on Java models

-- Table: users
-- Note: This table is likely managed by a separate auth/user service.
-- It's included here as a foreign key reference.
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL, -- bcrypt hash
    role VARCHAR(50) NOT NULL, -- e.g., BUYER, SELLER, ADMIN
    is_verified BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE
);

-- Table: addresses
CREATE TABLE addresses (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    address_type VARCHAR(50) DEFAULT 'SHIPPING', -- e.g., BILLING, SHIPPING, WAREHOUSE
    company_name VARCHAR(100),
    street_address_1 VARCHAR(255) NOT NULL,
    street_address_2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table: categories
CREATE TABLE categories (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url VARCHAR(255), -- Made nullable based on common use cases
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: warehouses
CREATE TABLE warehouses (
    warehouse_id VARCHAR(36) PRIMARY KEY,
    seller_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address_id VARCHAR(36) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE RESTRICT -- Prevent deleting address if warehouse uses it
);

-- Table: products
CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY,
    seller_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id VARCHAR(36),
    base_price DECIMAL(12, 2),
    minimum_order_quantity INT DEFAULT 1,
    weight DECIMAL(10, 2),
    weight_unit VARCHAR(10) DEFAULT 'kg',
    dimensions VARCHAR(50),
    sku VARCHAR(50) UNIQUE,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

-- Table: product_variants
CREATE TABLE product_variants (
    id VARCHAR(36) PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    sku VARCHAR(50) UNIQUE NOT NULL,
    variant_name VARCHAR(255),
    base_price DECIMAL(12, 2) NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Table: variant_price_tiers
CREATE TABLE variant_price_tiers (
    id VARCHAR(36) PRIMARY KEY,
    variant_id VARCHAR(36) NOT NULL,
    minimum_quantity INT NOT NULL CHECK (minimum_quantity > 0),
    max_quantity INT,
    price_per_unit DECIMAL(12, 2) NOT NULL CHECK (price_per_unit > 0),
    discount_percent DECIMAL(5, 2) NOT NULL DEFAULT 0.00 CHECK (discount_percent >= 0),
    is_active BOOLEAN DEFAULT TRUE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE,
    CONSTRAINT min_max_quantity_check CHECK (max_quantity IS NULL OR max_quantity >= minimum_quantity)
);

-- Table: product_images
CREATE TABLE product_images (
    id VARCHAR(36) PRIMARY KEY,
 -- product_id is not directly on ProductImage in the latest model, it's via ProductVariant
    variant_id VARCHAR(36), -- variant_id can be nullable if image is product-level
    image_url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 -- FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE, -- Removed based on model
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE
);

-- Table: product_specifications
CREATE TABLE product_specifications (
    id VARCHAR(36) PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    spec_name VARCHAR(100) NOT NULL,
    spec_value TEXT, -- Made nullable as per typical specification data, value might be null
    unit VARCHAR(50),
 display_order INT DEFAULT 0,
 variant_id VARCHAR(36), -- Added variant_id as per model
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE -- Link to variant
);

-- Table: inventory
CREATE TABLE inventory (
    id VARCHAR(36) PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36),
    warehouse_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    reserved_quantity INT NOT NULL DEFAULT 0 CHECK (reserved_quantity >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE,
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id) ON DELETE CASCADE,
    UNIQUE (product_id, variant_id, warehouse_id) -- Ensure unique entry per product/variant and warehouse
);

-- Table: contracts
CREATE TABLE contracts (
    id VARCHAR(36) PRIMARY KEY,
    buyer_id VARCHAR(36) NOT NULL,
    seller_id VARCHAR(36) NOT NULL,
    contract_number VARCHAR(50) UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    payment_terms INT DEFAULT 30,
    credit_limit DECIMAL(12, 2),
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT', -- e.g., DRAFT, ACTIVE, EXPIRED, TERMINATED
    terms_and_conditions TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table: contract_items
CREATE TABLE contract_items (
    id VARCHAR(36) PRIMARY KEY,
    contract_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36),
    negotiated_price DECIMAL(12, 2) NOT NULL CHECK (negotiated_price > 0),
    min_commitment_quantity INT CHECK (min_commitment_quantity > 0),
    max_quantity INT CHECK (max_quantity > 0),
    discount_percent DECIMAL(5, 2) NOT NULL DEFAULT 0.00 CHECK (discount_percent >= 0),
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (contract_id) REFERENCES contracts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE
);

-- Table: credit_applications
CREATE TABLE credit_applications (
    id VARCHAR(36) PRIMARY KEY,
    buyer_id VARCHAR(36) NOT NULL,
    seller_id VARCHAR(36) NOT NULL,
    requested_limit DECIMAL(12, 2) NOT NULL,
    approved_limit DECIMAL(12, 2),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- e.g., PENDING, APPROVED, REJECTED, EXPIRED
    application_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    review_date TIMESTAMP,
    expiry_date DATE,
    reviewer_id VARCHAR(36),
    notes TEXT,
    updated_at TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES users (id) ON DELETE SET NULL
);

-- Table: orders
CREATE TABLE orders (
    id VARCHAR(36) PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE,
    buyer_id VARCHAR(36) NOT NULL,
    seller_id VARCHAR(36) NOT NULL,
    contract_id VARCHAR(36),
    shipping_address_id VARCHAR(36) NOT NULL,
    billing_address_id VARCHAR(36) NOT NULL,
    payment_method VARCHAR(50) DEFAULT 'BANK_TRANSFER', -- e.g., CASH, BANK_TRANSFER, CREDIT, CARD
    reference_number VARCHAR(50),
    purchase_order_number VARCHAR(50),
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    notes TEXT,
    status VARCHAR(50) DEFAULT 'DRAFT', -- e.g., DRAFT, PENDING_APPROVAL, APPROVED, REJECTED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    subtotal DECIMAL(12, 2) DEFAULT 0.00,
    tax_amount DECIMAL(12, 2) DEFAULT 0.00,
    shipping_cost DECIMAL(12, 2) DEFAULT 0.00,
    discount_amount DECIMAL(12, 2) DEFAULT 0.00,
    total_price DECIMAL(12, 2) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (contract_id) REFERENCES contracts (id) ON DELETE SET NULL,
    FOREIGN KEY (shipping_address_id) REFERENCES addresses (id) ON DELETE RESTRICT,
    FOREIGN KEY (billing_address_id) REFERENCES addresses (id) ON DELETE RESTRICT
);

-- Table: order_items
CREATE TABLE order_items (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36),
    quantity INT NOT NULL CHECK (quantity > 0),
    price_per_unit DECIMAL(12, 2) NOT NULL CHECK (price_per_unit > 0),
    discount_percent DECIMAL(5, 2) DEFAULT 0.00 CHECK (discount_percent >= 0),
    tax_percent DECIMAL(5, 2) DEFAULT 0.00 CHECK (tax_percent >= 0),
    total_price DECIMAL(12, 2) NOT NULL CHECK (total_price > 0),
    ship_from_warehouse_id VARCHAR(36),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- e.g., PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    notes TEXT,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT, -- Prevent deleting product if it's in an order
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE RESTRICT, -- Prevent deleting variant if it's in an order
    FOREIGN KEY (ship_from_warehouse_id) REFERENCES warehouses (warehouse_id) ON DELETE SET NULL
);

-- Table: order_approvals
CREATE TABLE order_approvals (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    approver_id VARCHAR(36) NOT NULL,
    approval_level INT DEFAULT 1,
    status VARCHAR(50) DEFAULT 'PENDING', -- e.g., PENDING, APPROVED, REJECTED
    comments TEXT,
    approval_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (approver_id) REFERENCES users (id) ON DELETE RESTRICT -- Prevent deleting user if they are an approver
);

-- Table: payments
CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    payment_reference VARCHAR(100) UNIQUE,
    amount DECIMAL(12, 2) NOT NULL CHECK (amount > 0),
    payment_type VARCHAR(50) NOT NULL, -- e.g., CASH, BANK_TRANSFER, CREDIT, CARD
    transaction_id VARCHAR(100),
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- e.g., PENDING, SUCCESS, FAILED, REFUNDED, CANCELLED
    payment_date TIMESTAMP,
    due_date DATE,
    notes VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

-- Table: quotes
CREATE TABLE quotes (
    id VARCHAR(36) PRIMARY KEY,
    quote_number VARCHAR(50) UNIQUE,
    buyer_id VARCHAR(36) NOT NULL,
    seller_id VARCHAR(36) NOT NULL,
    status VARCHAR(50) DEFAULT 'DRAFT', -- e.g., DRAFT, PENDING, APPROVED, REJECTED, ACCEPTED, EXPIRED
    expiration_date DATE,
    notes TEXT,
    subtotal DECIMAL(12, 2) DEFAULT 0.00,
    tax_amount DECIMAL(12, 2) DEFAULT 0.00,
    shipping_cost DECIMAL(12, 2) DEFAULT 0.00,
    discount_amount DECIMAL(12, 2) DEFAULT 0.00,
    total_price DECIMAL(12, 2) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table: quote_items
CREATE TABLE quote_items (
    id VARCHAR(36) PRIMARY KEY,
    quote_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36),
    quantity INT NOT NULL CHECK (quantity > 0),
    price_per_unit DECIMAL(12, 2) NOT NULL CHECK (price_per_unit > 0),
    discount_percent DECIMAL(5, 2) DEFAULT 0.00 CHECK (discount_percent >= 0),
    tax_percent DECIMAL(5, 2) DEFAULT 0.00 CHECK (tax_percent >= 0),
    total_price DECIMAL(12, 2) NOT NULL CHECK (total_price > 0),
    notes TEXT,
    FOREIGN KEY (quote_id) REFERENCES quotes (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE
);