package com.saudiMart.Product.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedAtAfter(LocalDateTime createdAt);

    List<Order> findByBuyerAndSeller(Users BuyerUser, Users sellerUser);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findBySeller(Users user);

    List<Order> findByBuyer(Users user);
}
