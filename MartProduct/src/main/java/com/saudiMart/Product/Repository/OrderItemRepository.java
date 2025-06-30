package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.Products;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    Page<OrderItem> findByOrder(Order order, Pageable pageable);

    Page<OrderItem> findByProduct(Products product, Pageable pageable);

    Page<OrderItem> findByOrderAndProduct(Order order, Products product, Pageable pageable);

    Page<OrderItem> findAll(Specification<OrderItem> spec, Pageable pageable);

}