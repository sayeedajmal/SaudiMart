package com.saudiMart.Product.Model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "contract_items")
@Data
public class ContractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @NotNull
    @Positive(message = "Negotiated price must be positive")
    @Column(name = "negotiated_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal negotiatedPrice;

    @Column(name = "min_commitment_quantity")
    @Positive(message = "Minimum commitment quantity must be positive")
    private Integer minCommitmentQuantity;

    @Column(name = "max_quantity")
    @Positive(message = "Maximum quantity must be positive")
    private Integer maxQuantity;

    @NotNull
    @Column(name = "discount_percent", nullable = false, precision = 5, scale = 2)
    @PositiveOrZero(message = "Discount percent must be zero or positive")
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "is_active")
    private Boolean isActive = true;
}