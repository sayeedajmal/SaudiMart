package com.saudiMart.Product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {

    List<ProductVariant> findByProduct(Products product);
    Page<ProductVariant> findByProduct(Products product, Pageable pageable);

    Optional<ProductVariant> findBySku(String sku);

    List<ProductVariant> findByAvailableTrue();
    Page<ProductVariant> findByAvailableTrue(Pageable pageable);

    List<ProductVariant> findByProductAndAvailableTrue(Products product);
    Page<ProductVariant> findByProductAndAvailableTrue(Products product, Pageable pageable);

    void deleteByProduct(Products product);

    @Query("SELECT pv FROM ProductVariant pv WHERE " +
           "(:product is null or pv.product = :product) and " +
           "(:sku is null or lower(pv.sku) like lower(concat('%', :sku, '%'))) and " +
           "(:available is null or pv.available = :available)")
    Page<ProductVariant> searchProductVariants(@Param("product") Products product, @Param("sku") String sku, @Param("available") Boolean available, Pageable pageable);
}
