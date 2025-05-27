package com.saudimart.martProduct.Repository;

import com.saudimart.martProduct.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProduct_ProductId(Long productId);

    List<ProductImage> findByProductVariant_VariantId(Long variantId);
}