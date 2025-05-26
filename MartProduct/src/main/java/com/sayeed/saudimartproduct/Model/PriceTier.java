package com.sayeed.saudimartproduct.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "price_tiers")
@Data
public class PriceTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long tierId;

    @NotNull(message = "Product variant cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant productVariant;

    @NotNull(message = "Minimum quantity cannot be null")
    @Positive(message = "Minimum quantity must be positive")
    @Column(name = "minimum_quantity", nullable = false)
    private Integer minimumQuantity;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
}