package com.saudiMart.Product.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductVariant;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, String> {

    List<PriceTier> findByMinQuantityGreaterThanEqual(Integer minQty);

    List<PriceTier> findByPricePerUnitLessThanEqual(BigDecimal maxPrice);

    List<PriceTier> findByVariantAndIsActiveTrue(ProductVariant variant);

    List<PriceTier> findByVariant(ProductVariant variant);
}
