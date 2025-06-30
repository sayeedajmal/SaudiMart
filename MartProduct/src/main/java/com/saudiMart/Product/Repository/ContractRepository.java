package com.saudiMart.Product.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.Users;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    Page<Contract> findByStatus(Contract.ContractStatus status, Pageable pageable);

    Page<Contract> findByBuyer(Users user, Pageable pageable);

    Page<Contract> findBySeller(Users user, Pageable pageable);

    Page<Contract> findAll(Specification<Contract> spec, Pageable pageable);

    Optional<ContractItem> findByContract(Contract contract);
}