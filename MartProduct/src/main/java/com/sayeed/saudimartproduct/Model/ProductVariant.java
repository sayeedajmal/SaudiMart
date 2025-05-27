package com.saudimart.martProduct.Model;

import com.saudimart.martProduct.Model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "product_variants")
@Data
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Long variantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product cannot be null")
    private Product product;

    @NotBlank(message = "SKU cannot be blank")
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(name = "variant_name")
    private String variantName;

    @NotNull(message = "Additional price cannot be null")
    @PositiveOrZero(message = "Additional price must be zero or positive")
    @Column(name = "additional_price", precision = 10, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Column(name = "available")
    private Boolean available = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}