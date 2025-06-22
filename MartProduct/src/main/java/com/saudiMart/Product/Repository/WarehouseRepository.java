package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}