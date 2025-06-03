package com.saudiMart.Product.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.Products;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {

    // ✔ Find all price tiers for a given product

    // ✔ Optional: find all active price tiers for a given product
    List<PriceTier> findByProductAndIsActiveTrue(Products product);

    // ✔ Find price tiers with minimum quantity greater than or equal to a value
    List<PriceTier> findByMinimumQuantityGreaterThanEqual(Integer minQty);

    // ✔ Find price tiers with price less than or equal to a value
    List<PriceTier> findByPriceLessThanEqual(BigDecimal maxPrice);

    // Add the following methods as instructed
    Optional<PriceTier> findByTierId(Long tierId);

    List<PriceTier> findByProduct(Products product);

    List<PriceTier> findByProductId(Long productId);

    List<PriceTier> findByMinimumQuantity(Integer minimumQuantity);

    List<PriceTier> findByPricePerUnit(BigDecimal pricePerUnit);

    List<PriceTier> findByMaxQuantity(Integer maxQuantity);

    List<PriceTier> findByIsActive(Boolean isActive);

    List<PriceTier> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Optional<PriceTier> findByProductAndMinimumQuantityLessThanEqualAndMaxQuantityGreaterThanEqual(Products product,
            Integer quantity1, Integer quantity2);

    Optional<PriceTier> findByProductIdAndMinimumQuantityLessThanEqualAndMaxQuantityGreaterThanEqual(Long productId,
            Integer quantity1, Integer quantity2);
}
