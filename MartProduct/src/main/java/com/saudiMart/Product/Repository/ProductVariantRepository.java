package com.saudiMart.Product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProduct(Products product);

    Optional<ProductVariant> findBySku(String sku);

    List<ProductVariant> findByAvailableTrue();

    List<ProductVariant> findByProductAndAvailableTrue(Products product);

    void deleteByProduct(Products product);
}
