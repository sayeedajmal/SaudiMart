package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}