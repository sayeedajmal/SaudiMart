package com.saudimart.martProduct.Model;

import com.saudimart.martProduct.Model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "product_images")
import jakarta.persistence.*;
@Data
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Optional: Image can be for a product

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant productVariant; // Optional: Image can be for a variant

    @NotBlank(message = "Image URL cannot be blank")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder;

    @NotNull(message = "Is primary field cannot be null")
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private Timestamp uploadedAt;

    // Consider adding a field for the image type if needed (e.g., "thumbnail", "large")
    // @Column(name = "image_type")
    // private String imageType;
}