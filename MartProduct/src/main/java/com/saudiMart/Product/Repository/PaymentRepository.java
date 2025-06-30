package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.Payment.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Page<Payment> findByOrder(Order order, Pageable pageable);

    Page<Payment> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    Page<Payment> findAll(Specification<Payment> spec, Pageable pageable);
}