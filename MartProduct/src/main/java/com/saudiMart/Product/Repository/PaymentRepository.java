package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrder(Order order);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}