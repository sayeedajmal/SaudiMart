package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.Products;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByProduct(Products product);

    List<OrderItem> findByOrderAndProduct(Order order, Products product);

}