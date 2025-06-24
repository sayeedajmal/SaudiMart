package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.Payment.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrder(Order order);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}