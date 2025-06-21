package com.saudiMart.Product.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.Products;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {

    List<PriceTier> findByProduct(Products product);

    List<PriceTier> findByProductAndIsActiveTrue(Products product);

    List<PriceTier> findByMinimumQuantityGreaterThanEqual(Integer minQty);

    List<PriceTier> findByPriceLessThanEqual(BigDecimal maxPrice);

    void deleteByProduct(Products product);
}
