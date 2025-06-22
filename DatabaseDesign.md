# 🏢 B2B E-Commerce Database Schema

A comprehensive, production-ready database schema designed specifically for B2B e-commerce platforms with advanced features like bulk pricing, contract management, multi-warehouse inventory, and approval workflows.

## 📋 Table of Contents

- [🏗️ Schema Overview](#️-schema-overview)
- [🔧 Key Features](#-key-features)
- [📊 Database Structure](#-database-structure)
- [🔄 Table Dependencies](#-table-dependencies)
- [💰 Pricing Strategy](#-pricing-strategy)
- [🚀 Installation](#-installation)
- [📈 Performance Optimizations](#-performance-optimizations)
- [🔒 Data Integrity](#-data-integrity)

## 🏗️ Schema Overview

This database schema supports a full-featured B2B e-commerce platform with:

- **Multi-role user management** (Buyers, Sellers, Admins)
- **Hierarchical product categories** with variants
- **Flexible pricing** (variant-based + bulk tiers)
- **Multi-warehouse inventory** management
- **Contract-based trading** with credit limits
- **Quote-to-order** workflow
- **Multi-level approval** processes
- **Comprehensive payment** tracking

## 🔧 Key Features

### ✅ Fixed Issues from Original Schema
- **Proper table ordering** - No forward references
- **Variant-based pricing** - Each variant has its own base price
- **Cascade deletes** - Proper cleanup of related records
- **Data validation** - Constraints for business rules
- **Performance indexes** - Optimized for common queries
- **Auto-calculations** - Triggers for order totals

### 🎯 B2B Specific Features
- **Bulk pricing tiers** per product variant
- **Contract management** with negotiated prices
- **Credit applications** and limits
- **Purchase order** integration
- **Multi-level approvals** for large orders
- **Warehouse-specific** inventory tracking

## 📊 Database Structure

### 1. Core Reference Tables

#### Categories
```sql
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id)
);
```

#### Users
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    password_hash TEXT NOT NULL,
    role ENUM('BUYER','SELLER','ADMIN') NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 2. Address Management

#### Addresses
```sql
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
```

### 3. Product Management

#### Products
```sql
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
```

#### Product Variants (with Base Pricing)
```sql
CREATE TABLE product_variants (
    variant_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    variant_name VARCHAR(100),
    base_price DECIMAL(12,2) NOT NULL, -- Each variant has its own base price
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
```

#### Variant Price Tiers (Bulk Pricing)
```sql
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
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE CASCADE
);
```

### 4. Inventory Management

#### Warehouses
```sql
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
```

#### Inventory
```sql
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
    UNIQUE KEY unique_inventory (product_id, variant_id, warehouse_id)
);
```

### 5. Contract Management

#### Contracts
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
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### 6. Order Management

#### Orders
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
    FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id) ON DELETE SET NULL,
    FOREIGN KEY (shipping_address_id) REFERENCES addresses(address_id) ON DELETE RESTRICT,
    FOREIGN KEY (billing_address_id) REFERENCES addresses(address_id) ON DELETE RESTRICT
);
```

#### Order Items
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
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE RESTRICT,
    FOREIGN KEY (ship_from_warehouse_id) REFERENCES warehouses(warehouse_id) ON DELETE SET NULL
);
```

### 7. Payment Management

#### Payments
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
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);
```

## 🔄 Table Dependencies

```
Independent Tables:
├── categories (self-referencing)
└── users

Level 1 Dependencies:
├── addresses (users)
├── products (users, categories)
└── credit_applications (users)

Level 2 Dependencies:
├── warehouses (users, addresses)
├── product_variants (products)
├── product_images (products, variants)
├── product_specifications (products)
├── contracts (users)
└── quotes (users)

Level 3 Dependencies:
├── variant_price_tiers (variants)
├── inventory (products, variants, warehouses)
├── contract_items (contracts, products, variants)
├── quote_items (quotes, products, variants)
└── orders (users, contracts, addresses)

Level 4 Dependencies:
├── order_items (orders, products, variants, warehouses)
├── order_approvals (orders, users)
└── payments (orders)
```

## 💰 Pricing Strategy

### How Pricing Works:

1. **Base Price**: Each product variant has its own `base_price`
2. **Bulk Tiers**: Volume discounts apply per variant via `variant_price_tiers`
3. **Contract Pricing**: Special negotiated prices in `contract_items`
4. **Final Calculation**: System picks the best price (contract vs bulk tiers)

### Example:
```
Product: "Industrial Laptop"
├── Variant: "16GB RAM, 512GB SSD" (base_price: $1200)
│   ├── Tier 1: 1-10 units = $1200/unit
│   ├── Tier 2: 11-50 units = $1100/unit  
│   └── Tier 3: 51+ units = $1000/unit
└── Variant: "32GB RAM, 1TB SSD" (base_price: $1600)
    ├── Tier 1: 1-10 units = $1600/unit
    └── Tier 2: 11+ units = $1450/unit
```

## 🚀 Installation

### 1. Create Database
```sql
CREATE DATABASE b2b_ecommerce;
USE b2b_ecommerce;
```

### 2. Run Schema
```bash
# Execute the complete schema file
mysql -u username -p b2b_ecommerce < complete_schema.sql
```

### 3. Verify Installation
```sql
-- Check all tables created
SHOW TABLES;

-- Verify constraints
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
WHERE TABLE_SCHEMA = 'b2b_ecommerce';

-- Check indexes
SHOW INDEX FROM products;
```

## 📈 Performance Optimizations

### Indexes Created:
- **Primary Keys**: Auto-indexed
- **Foreign Keys**: All indexed for JOIN performance
- **Business Logic**: Email, SKU, status fields
- **Composite Indexes**: buyer_id + seller_id for orders
- **Unique Constraints**: Prevent duplicate default addresses

### Query Optimization:
```sql
-- Fast product search with category and seller
SELECT p.*, pv.base_price, c.name as category_name
FROM products p
JOIN product_variants pv ON p.product_id = pv.product_id
JOIN categories c ON p.category_id = c.category_id
WHERE p.seller_id = ? AND p.available = TRUE;

-- Efficient inventory check across warehouses
SELECT SUM(quantity - reserved_quantity) as available_stock
FROM inventory 
WHERE product_id = ? AND variant_id = ?;

-- Fast order history with pagination
SELECT * FROM orders 
WHERE buyer_id = ? 
ORDER BY created_at DESC 
LIMIT 20 OFFSET ?;
```

## 🔒 Data Integrity

### Business Rules Enforced:
- ✅ **No self-trading**: Buyer ≠ Seller in orders/contracts
- ✅ **Positive amounts**: All prices, quantities > 0
- ✅ **Valid percentages**: Discounts, taxes between 0-100%
- ✅ **Logical dates**: End dates > Start dates
- ✅ **Inventory limits**: Reserved ≤ Available quantity
- ✅ **Email validation**: Proper email format
- ✅ **Unique defaults**: One default address per user per type

### Automatic Calculations:
- 🔄 **Order totals**: Auto-calculated from order items
- 🔄 **Inventory updates**: Reserved quantity management
- 🔄 **Timestamps**: Auto-updated on record changes

### Cascade Rules:
- 🗑️ **User deleted**: Cascade to addresses, products, contracts
- 🗑️ **Product deleted**: Cascade to variants, images, specs
- 🗑️ **Order deleted**: Cascade to order items, payments
- 🛡️ **Order referenced**: Restrict user/address deletion

## 📝 Usage Examples

### Insert Sample Data:
```sql
-- Create a seller
INSERT INTO users (name, email, role, password_hash) 
VALUES ('TechCorp Ltd', 'sales@techcorp.com', 'SELLER', 'hashed_password');

-- Add product with variants
INSERT INTO products (seller_id, name, category_id, sku) 
VALUES (1, 'Industrial Laptop', 1, 'LAPTOP-001');

INSERT INTO product_variants (product_id, variant_name, sku, base_price) 
VALUES (1, '16GB RAM, 512GB SSD', 'LAPTOP-001-16-512', 1200.00);

-- Set bulk pricing
INSERT INTO variant_price_tiers (variant_id, min_quantity, max_quantity, price_per_unit) 
VALUES 
(1, 1, 10, 1200.00),
(1, 11, 50, 1100.00),
(1, 51, NULL, 1000.00);
```

### Complex Queries:
```sql
-- Get best price for a product variant at specific quantity
SELECT 
    CASE 
        WHEN ci.negotiated_price IS NOT NULL THEN ci.negotiated_price
        ELSE COALESCE(vpt.price_per_unit, pv.base_price)
    END as final_price
FROM product_variants pv
LEFT JOIN variant_price_tiers vpt ON pv.variant_id = vpt.variant_id 
    AND ? BETWEEN vpt.min_quantity AND COALESCE(vpt.max_quantity, 999999)
LEFT JOIN contract_items ci ON pv.variant_id = ci.variant_id 
    AND ci.contract_id = ?
WHERE pv.variant_id = ?
ORDER BY final_price ASC
LIMIT 1;
```

---

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📄 License

This database schema is provided as-is for educational and commercial use.