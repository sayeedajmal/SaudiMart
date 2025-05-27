package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.ProductSpecification;

import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> {

    List<ProductSpecification> findByProduct_ProductId(Long productId);
}