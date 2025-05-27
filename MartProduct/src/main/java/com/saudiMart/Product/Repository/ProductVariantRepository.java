package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.ProductVariant;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);
}