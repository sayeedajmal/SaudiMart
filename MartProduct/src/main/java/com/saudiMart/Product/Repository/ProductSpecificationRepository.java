package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> {

    List<ProductSpecification> findByProduct(Products product);

    List<ProductSpecification> findByProductOrderByDisplayOrderAsc(Products product);

    List<ProductSpecification> findBySpecNameContainingIgnoreCase(String keyword);

    void deleteByProduct(Products product);
}
