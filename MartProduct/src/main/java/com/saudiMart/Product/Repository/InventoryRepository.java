package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProduct(Products product);

    void deleteByProduct(Products product);
}