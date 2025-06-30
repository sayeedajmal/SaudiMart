package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ContractItemRepository extends JpaRepository<ContractItem, String> {

    Page<ContractItem> findByContract(Contract contract, Pageable pageable);

    Page<ContractItem> findByProduct(Products product, Pageable pageable);

    Page<ContractItem> findByVariant(ProductVariant variant, Pageable pageable);

    Page<ContractItem> findAll(Specification<ContractItem> spec, Pageable pageable);
}