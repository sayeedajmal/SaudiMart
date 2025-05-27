package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.PriceTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {
    List<PriceTier> findByProductVariant_VariantId(Long variantId);
}