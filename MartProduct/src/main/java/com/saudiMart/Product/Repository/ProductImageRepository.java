package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProduct_ProductId(Long productId);

    List<ProductImage> findByProductVariant_VariantId(Long variantId);
}