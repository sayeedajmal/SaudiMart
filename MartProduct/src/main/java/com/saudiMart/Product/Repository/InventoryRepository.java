package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    Page<Inventory> findByProductAndVariantAndWarehouse(Products product, ProductVariant variant, Warehouse warehouse,
            Pageable pageable);

    Page<Inventory> findByProductAndVariant(Products product, ProductVariant variant, Pageable pageable);

}