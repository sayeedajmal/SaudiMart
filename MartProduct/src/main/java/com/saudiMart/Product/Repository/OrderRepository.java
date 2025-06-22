package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByBuyerId(Long buyerId);

    List<Order> findBySellerId(Long sellerId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedAtAfter(LocalDateTime createdAt);

    List<Order> findByBuyerIdAndSellerId(Long buyerId, Long sellerId);
}