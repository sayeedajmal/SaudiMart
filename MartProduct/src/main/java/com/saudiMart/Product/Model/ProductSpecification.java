package com.saudiMart.Product.Model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "product_specifications")
@Data
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specification_id")
    private Long specificationId;

    @NotNull(message = "Product cannot be null")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "Specification name cannot be blank")
    @Column(name = "spec_name", nullable = false)
    private String specName;

    @Column(name = "spec_value")
    private String specValue;

    @Column(name = "unit")
    private String unit; // e.g., kg, cm, Fahrenheit

}