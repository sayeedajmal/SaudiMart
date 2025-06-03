package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    // You can add custom query methods here if needed
    List<Products> findBySellerId(Long sellerId);

    List<Products> findByCategoryId(Long categoryId);

    List<Products> findByName(String name);

    // Find products with a name containing a given string (case-insensitive)
    List<Products> findByNameContainingIgnoreCase(String name);

    List<Products> findBySku(String sku);

    List<Products> findByAvailable(Boolean available);

    Optional<Products> findByProductId(Long productId);

    List<Products> findByBasePrice(BigDecimal basePrice);

    List<Products> findByIsBulkOnly(Boolean isBulkOnly);

    List<Products> findByMinimumOrderQuantity(Integer minimumOrderQuantity);

    List<Products> findByWeight(BigDecimal weight);

    List<Products> findByWeightUnit(String weightUnit);

    List<Products> findByDimensions(String dimensions);

    List<Products> findByCreatedAtBetween(Timestamp start, Timestamp end);

    List<Products> findByUpdatedAtBetween(Timestamp start, Timestamp end);

    List<Products> findBySellerIdAndCategoryId(Long sellerId, Long categoryId);

    // Find products with a description containing a given string (case-insensitive)
    List<Products> findByDescriptionContainingIgnoreCase(String description);

}
