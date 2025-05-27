package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}