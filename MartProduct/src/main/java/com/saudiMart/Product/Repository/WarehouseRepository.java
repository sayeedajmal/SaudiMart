package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

        Page<Warehouse> findBySeller(Users user, Pageable pageable);

        @SuppressWarnings("null")
        Page<Warehouse> findAll(Pageable pageable);

        Page<Warehouse> findByNameContainingIgnoreCaseOrSeller(String name, Users seller, Pageable pageable);
}