package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProduct(Products product);

    Optional<ProductVariant> findBySku(String sku);

    List<ProductVariant> findByAvailableTrue();

    List<ProductVariant> findByProductAndAvailableTrue(Products product);
}
