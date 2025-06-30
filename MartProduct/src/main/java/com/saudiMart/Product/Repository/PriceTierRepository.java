package com.saudiMart.Product.Repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductVariant;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, String> {

    // Pagination for finding all PriceTiers is inherited from JpaRepository

    Page<PriceTier> findByMinQuantityGreaterThanEqual(Integer minQty, Pageable pageable);

    Page<PriceTier> findByMaxQuantityLessThanEqual(Integer maxQty, Pageable pageable);

    Page<PriceTier> findByPricePerUnitBetween(BigDecimal minPricePerUnit, BigDecimal maxPricePerUnit, Pageable pageable);

    Page<PriceTier> findByVariantAndIsActiveTrue(ProductVariant variant, Pageable pageable);

    Page<PriceTier> findByVariant(ProductVariant variant, Pageable pageable);

    @Query("SELECT pt FROM PriceTier pt WHERE " +
           "(:variant is null or pt.variant = :variant) and " +
           "(:minQuantity is null or pt.minQuantity >= :minQuantity) and " +
           "(:maxQuantity is null or pt.maxQuantity <= :maxQuantity) and " +
           "(:minPricePerUnit is null or pt.pricePerUnit >= :minPricePerUnit) and " +
           "(:maxPricePerUnit is null or pt.pricePerUnit <= :maxPricePerUnit) and " +
           "(:isActive is null or pt.isActive = :isActive)")
    Page<PriceTier> searchPriceTiers(ProductVariant variant, Integer minQuantity, Integer maxQuantity,
                                     BigDecimal minPricePerUnit, BigDecimal maxPricePerUnit, Boolean isActive,
                                     Pageable pageable);
}
