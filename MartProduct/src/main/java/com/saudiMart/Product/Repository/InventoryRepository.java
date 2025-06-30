package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Warehouse;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Page<Inventory> findByProduct(Products product, Pageable pageable);

    void deleteByProduct(Products product);

    Page<Inventory> findByVariant(ProductVariant variant, Pageable pageable);

    Page<Inventory> findByWarehouse(Warehouse warehouse, Pageable pageable);
}