package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractItemRepository extends JpaRepository<ContractItem, String> {

 Page<ContractItem> findByContract(Contract contract, Pageable pageable);

 Page<ContractItem> findByProduct(Products product, Pageable pageable);

 Page<ContractItem> findByVariant(ProductVariant variant, Pageable pageable);
}