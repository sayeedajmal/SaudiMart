package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saudiMart.Product.Model.Products;

import java.util.List;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> { 
    // You can add custom query methods here if needed 
    List<ProductSpecification> findByProduct(Products product); 
    List<ProductSpecification> findBySpecName(String specName); 
    List<ProductSpecification> findByUnit(String unit); 
    List<ProductSpecification> findByProductId(Long productId); 
 List<ProductSpecification> findBySpecNameContainingIgnoreCase(String specName);

    // Find a product specification by its ID
    ProductSpecification findBySpecId(Long specId);

    // Find product specifications by spec value
    List<ProductSpecification> findBySpecValue(String specValue);

    // Find product specifications by display order
    List<ProductSpecification> findByDisplayOrder(Integer displayOrder);

    // Find a product specification by product ID and spec name
    ProductSpecification findByProductIdAndSpecName(Long productId, String specName);

    // Find product specifications for a given product, ordered by display order
    List<ProductSpecification> findByProductIdOrderByDisplayOrderAsc(Long productId);

}