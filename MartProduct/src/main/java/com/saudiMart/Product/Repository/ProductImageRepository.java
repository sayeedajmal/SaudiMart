package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import com.saudiMart.Product.Model.Products;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByImageId(Long imageId);
    List<ProductImage> findByProduct(Products product);
    List<ProductImage> findByProductId(Long productId);
    List<ProductImage> findByVariantId(Long variantId);
    List<ProductImage> findByImageUrl(String imageUrl);
    List<ProductImage> findByAltText(String altText);
    List<ProductImage> findByDisplayOrder(Integer displayOrder);
    List<ProductImage> findByIsPrimary(Boolean isPrimary);
    List<ProductImage> findByCreatedAtBetween(Timestamp start, Timestamp end);
    
    // Custom methods for common use cases
    List<ProductImage> findByProductIdAndIsPrimaryTrue(Long productId);
    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Long productId);
    List<ProductImage> findByProductIdAndVariantIdOrderByDisplayOrderAsc(Long productId, Long variantId);
}
