package com.saudiMart.Product.Repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

   Page<Order> findByStatus(OrderStatus status, Pageable pageable);

   Page<Order> findByCreatedAtAfter(LocalDateTime createdAt, Pageable pageable);

   Page<Order> findByBuyerAndSeller(Users BuyerUser, Users sellerUser, Pageable pageable);

   @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
   Page<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

   Page<Order> findBySeller(Users user, Pageable pageable);

   Page<Order> findByBuyer(Users user, Pageable pageable);
}
