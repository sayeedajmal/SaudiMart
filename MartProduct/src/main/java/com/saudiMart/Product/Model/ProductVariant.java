package com.saudiMart.Product.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "product_variants", indexes = {
        @Index(name = "idx_variants_product_id", columnList = "product_id"),
        @Index(name = "idx_variants_sku", columnList = "sku")
})
@Data
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference("product-variants")
    private Products product;

    @NotNull
    @Size(max = 50)
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "additional_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal additionalPrice;

    @NotNull
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;
    @Column(name = "available")
    private Boolean available = true;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
