package com.saudimart.martProduct.Repository;

import com.saudimart.martProduct.Model.PriceTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {

    List<PriceTier> findByProductVariant_VariantId(Long variantId);
}