package com.saudiMart.Product.Model;

import com.saudimart.martProduct.Model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "product_specifications")
@Data
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_id") // Changed from specification_id
    private Long specId; // Changed from specificationId

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

    @Column(name = "display_order")
    private Integer displayOrder; // Added displayOrder

}