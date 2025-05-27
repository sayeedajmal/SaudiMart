package com.saudiMart.Product.Model;

import com.saudimart.martProduct.Model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotNull(message = "Seller ID cannot be null")
    @Column(name = "seller_id", nullable = false)
    private Long sellerId; // Assuming seller is represented by ID from Auth service

    @NotBlank(message = "Product name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Base price cannot be null")
    @PositiveOrZero(message = "Base price must be zero or positive")
    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "is_bulk_only")
    private Boolean isBulkOnly = false;

    @PositiveOrZero(message = "Minimum order quantity must be zero or positive")
    @Column(name = "minimum_order_quantity")
    private Integer minimumOrderQuantity = 1;

    @PositiveOrZero(message = "Weight must be zero or positive")
    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "weight_unit")
    private String weightUnit = "kg";

    @Column(name = "dimensions")
    private String dimensions; // Format: LxWxH

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "available")
    private Boolean available = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}