package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

    List<Warehouse> findBySeller(Users user);

}