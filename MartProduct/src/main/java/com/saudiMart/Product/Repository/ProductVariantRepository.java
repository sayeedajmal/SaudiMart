package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    ProductVariant findByVariantId(Long variantId);

    List<ProductVariant> findByProductId(Long productId);

    ProductVariant findBySku(String sku);

    List<ProductVariant> findByVariantName(String variantName);

    List<ProductVariant> findByAdditionalDescriptionContainingIgnoreCase(String additionalDescription);


    List<ProductVariant> findByAvailable(Boolean available);

    List<ProductVariant> findByProductIdAndAvailable(Long productId, Boolean available);
}