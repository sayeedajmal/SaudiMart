package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByBuyerIdAndSellerId(Long buyerId, Long sellerId);

    List<Contract> findByStatus(Contract.ContractStatus status);
}