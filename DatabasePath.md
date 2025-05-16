### 1. **Users**

- **Purpose**: Stores user details such as buyers, sellers, and admins.
- **Used for**: User authentication, role management, and user-related activities.
- **Example Usage**: User registration, login, managing access to platform features based on roles (e.g., Buyer, Seller, Admin).

---

### 2. **Price Tiers**

- **Purpose**: Stores price tiers for products based on quantities.
- **Used for**: Pricing discounts based on the quantity purchased.
- **Example Usage**: A seller can apply different prices depending on how many units a buyer orders (e.g., bulk pricing).

---

### 3. **Contracts**

- **Purpose**: Stores contracts between buyers and sellers, including start/end dates, payment terms, and contract status.
- **Used for**: Managing B2B contracts, defining terms of trade.
- **Example Usage**: A buyer and seller sign a contract specifying terms like delivery, payment methods, and agreed prices.

---

### 4. **Contract Items**

- **Purpose**: Stores the individual products and their negotiated prices under each contract.
- **Used for**: Managing the specifics of each product or service agreed upon in a contract.
- **Example Usage**: A contract specifies the negotiated price for a product, which is captured here.

---

### 5. **Product Variants**

- **Purpose**: Stores different variants of a product (e.g., sizes, colors).
- **Used for**: Managing multiple versions of the same product.
- **Example Usage**: A T-shirt might come in different sizes or colors, each with a different SKU.

---

### 6. **Product Specifications**

- **Purpose**: Stores technical details and specifications of products.
- **Used for**: Providing detailed product info to buyers.
- **Example Usage**: A product like a laptop will have specifications like RAM size, screen size, processor type, etc.

---

### 7. **Warehouses**

- **Purpose**: Stores information about the seller's warehouse locations.
- **Used for**: Managing product stock levels, handling shipping, and warehouse operations.
- **Example Usage**: Products are stored in different warehouses for fulfillment.

---

### 8. **Inventory**

- **Purpose**: Tracks stock levels of products in specific warehouses.
- **Used for**: Managing the inventory of products and their availability for sale.
- **Example Usage**: A product in a warehouse has a certain number of units available for sale.

---

### 9. **Products**

- **Purpose**: Stores product information such as name, description, price, SKU, and availability.
- **Used for**: Listing products for sale on the platform.
- **Example Usage**: A seller adds products to the platform to be bought by buyers.

---

### 10. **Orders**

- **Purpose**: Stores details about orders placed by buyers, including products, quantities, shipping, and payment info.
- **Used for**: Managing the lifecycle of an order, including approval, processing, shipping, and delivery.
- **Example Usage**: A buyer places an order that includes several products and chooses a payment method.

---

### 11. **Payments**

- **Purpose**: Tracks payments made for orders, including payment status and transaction details.
- **Used for**: Managing the payment process for each order.
- **Example Usage**: A payment for an order is processed, and details are stored for future reference.

---

### 12. **Order Approvals**

- **Purpose**: Stores approval workflow for orders, particularly for higher-value or restricted purchases.
- **Used for**: Enforcing business rules where certain orders need approval before processing.
- **Example Usage**: A purchase order over a certain amount may require approval from a platform admin or buyer's manager.

---

### 13. **Order Items**

- **Purpose**: Stores individual items that are part of an order, including product details, quantities, and prices.
- **Used for**: Breaking down orders into individual items for processing, shipping, and payment.
- **Example Usage**: A buyer’s order includes multiple items, each with its own product details and price.

---

### 14. **Addresses**

- **Purpose**: Stores shipping and billing address information for users.
- **Used for**: Managing buyer and seller address details for order fulfillment and invoicing.
- **Example Usage**: When a buyer places an order, their shipping and billing address are stored for shipping the products.

---