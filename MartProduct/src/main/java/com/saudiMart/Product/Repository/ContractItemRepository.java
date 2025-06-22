package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractItemRepository extends JpaRepository<ContractItem, Long> {

    List<ContractItem> findByContract(Contract contract);

    List<ContractItem> findByProduct(Products product);

    List<ContractItem> findByVariant(ProductVariant variant);
}