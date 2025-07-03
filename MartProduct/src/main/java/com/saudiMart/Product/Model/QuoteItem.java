package com.saudiMart.Product.Model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "quote_items")
@Data
public class QuoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quote_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Quote quote;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @NotNull
    @Positive(message = "Quantity must be positive")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Positive(message = "Quoted price must be positive")
    @Column(name = "quoted_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal quotedPrice;

    @NotNull
    @Column(name = "discount_percent", nullable = false, precision = 5, scale = 2)
    @PositiveOrZero(message = "Discount percent must be zero or positive")
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @NotNull
    @PositiveOrZero(message = "Total price must be zero or positive")
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;
}