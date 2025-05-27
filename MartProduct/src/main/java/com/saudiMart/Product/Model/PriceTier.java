package com.saudiMart.Product.Model;

import java.math.BigDecimal;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "price_tiers")
@Data
public class PriceTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long tierId;

 @NotNull(message = "Product cannot be null")
 @ManyToOne
 @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Minimum quantity cannot be null")
    @Positive(message = "Minimum quantity must be positive")
 @Column(name = "min_quantity", nullable = false)
    private Integer minQuantity;

 @Column(name = "max_quantity")
    private Integer maxQuantity;


 @NotNull(message = "Price per unit cannot be null")
 @PositiveOrZero(message = "Price per unit must be zero or positive")
 @Column(name = "price_per_unit", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerUnit;

 @Column(name = "is_active")
    private Boolean isActive = true;

 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}