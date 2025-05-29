package com.saudiMart.Product.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "variant_id")
    @ManyToOne
    private Long variantId;

 @NotNull
    @Column(name = "image_url", nullable = false, length = 500)
 @Size(max = 500)
    private String imageUrl;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "created_at")
    private Timestamp createdAt;

    // Getters and setters

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }


    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt != null ? createdAt.toLocalDateTime() : null;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt != null ? Timestamp.valueOf(createdAt) : null;
    }

    public Products getProduct() {
        return product;
    }
}