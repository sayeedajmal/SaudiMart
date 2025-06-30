package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.Users;

public interface AddressRepository extends JpaRepository<Address, String> {

    Page<Address> findByUser(Users user, Pageable pageable);

    Page<Address> findByAddressType(Address.AddressType addressType, Pageable pageable);

    Page<Address> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);

    Page<Address> findByCityContainingIgnoreCase(String city, Pageable pageable);

    Page<Address> findByStateContainingIgnoreCase(String state, Pageable pageable);

    Page<Address> findByPostalCodeContainingIgnoreCase(String postalCode, Pageable pageable);

    Page<Address> findByCountryContainingIgnoreCase(String country, Pageable pageable);

}