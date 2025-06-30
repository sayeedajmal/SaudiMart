package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

    Page<Warehouse> findBySeller(Users user, Pageable pageable);

    @SuppressWarnings("null")
    Page<Warehouse> findAll(Pageable pageable);

    @Query("SELECT w FROM Warehouse w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(w.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Warehouse> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(@Param("keyword") String keyword,
            Pageable pageable);

    Page<Warehouse> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrSeller(String name, String location,
            Users seller, Pageable pageable);
}