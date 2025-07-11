package com.saudiMart.Product.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "variant_price_tiers")
@Data
public class PriceTier {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private String id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "variant_id", nullable = false)
   @JsonBackReference("variant-priceTiers")
   private ProductVariant variant;

   @NotNull
   @Positive(message = "Minimum quantity must be positive")
   @Column(name = "minimum_quantity", nullable = false)
   private Integer minQuantity;

   @Column(name = "max_quantity")
   private Integer maxQuantity;

   @NotNull
   @Positive(message = "Price per unit must be positive")
   @Column(name = "price_per_unit", nullable = false, precision = 12, scale = 2)
   private BigDecimal pricePerUnit;

   @NotNull
   @Column(name = "discount_percent", nullable = false, precision = 5, scale = 2)
   @PositiveOrZero(message = "Discount percent must be zero or positive")
   private BigDecimal discountPercent = BigDecimal.ZERO;

   @Column(name = "is_active")
   private Boolean isActive = true;

   @Column(name = "updated_at")
   private LocalDateTime updatedAt;

   @Column(name = "created_at")
   private LocalDateTime createdAt;

   @PrePersist
   protected void onCreate() {
      createdAt = LocalDateTime.now();
   }

}
