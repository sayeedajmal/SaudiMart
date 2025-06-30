package com.saudiMart.Product.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, String> {

    Page<ProductSpecification> findByProduct(Products product, Pageable pageable);

    Page<ProductSpecification> findByProductOrderByDisplayOrderAsc(Products product, Pageable pageable);

    Page<ProductSpecification> findBySpecNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<ProductSpecification> findByProductAndSpecNameContainingIgnoreCase(Products product, String specName, Pageable pageable);

    void deleteByProduct(Products product);
}
