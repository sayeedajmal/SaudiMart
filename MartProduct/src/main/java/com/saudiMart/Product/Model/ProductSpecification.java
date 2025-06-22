package com.saudiMart.Product.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "product_specifications")
@Data
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference("product-specifications")
    private Products product;

    @NotNull
    @Size(max = 100)
    @Column(name = "spec_name", nullable = false, length = 100)
    private String specName;

    @NotNull
    @Column(name = "spec_value", nullable = false, columnDefinition = "TEXT")
    private String specValue;

    @Size(max = 50)
    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "display_order")
    private Integer displayOrder = 0;
}
