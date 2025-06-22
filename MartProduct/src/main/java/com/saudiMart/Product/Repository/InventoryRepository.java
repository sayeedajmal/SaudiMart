package com.saudiMart.Product.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.Products;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProduct(Products product);

    void deleteByProduct(Products product);
}