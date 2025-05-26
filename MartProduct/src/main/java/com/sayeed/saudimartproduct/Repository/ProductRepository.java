package com.sayeed.saudimartproduct.Repository;

import com.sayeed.saudimartproduct.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}