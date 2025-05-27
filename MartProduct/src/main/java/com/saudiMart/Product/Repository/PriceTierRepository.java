package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.PriceTier;

public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {

    List<PriceTier> findByProductVariant_VariantId(Long variantId);
}