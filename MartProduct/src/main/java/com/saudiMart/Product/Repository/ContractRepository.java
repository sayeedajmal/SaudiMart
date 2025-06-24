package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByStatus(Contract.ContractStatus status);

    List<Contract> findByBuyer(Users user);

    List<Contract> findBySeller(Users user);
}