package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.Users;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(Users user);

}