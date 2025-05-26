package com.sayeed.saudimartproduct.Repository;

import com.sayeed.saudimartproduct.Model.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> {

    List<ProductSpecification> findByProduct_ProductId(Long productId);
}