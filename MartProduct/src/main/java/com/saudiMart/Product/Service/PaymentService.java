package com.saudiMart.Product.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Model.Payment.PaymentStatus;
import com.saudiMart.Product.Repository.PaymentRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<Payment> getAllPayments(Pageable pageable) {
 return paymentRepository.findAll(pageable);
    }

    public Payment getPaymentById(String id) throws ProductException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ProductException("Payment not found with id: " + id));
    }

    public Page<Payment> getPaymentsByOrder(String orderId, Pageable pageable) throws ProductException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProductException("Order not found with id: " + orderId));
        return paymentRepository.findByOrder(order, pageable);
    }

    public Page<Payment> getPaymentsByPaymentStatus(PaymentStatus status, Pageable pageable) {
        return paymentRepository.findByPaymentStatus(status, pageable);
    }

    public Payment createPayment(Payment payment) throws ProductException {
        if (payment == null) {
            throw new ProductException("Payment cannot be null");
        }
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(String id, Payment paymentDetails) throws ProductException {
        if (paymentDetails == null) {
            throw new ProductException("Payment details cannot be null for update");
        }
        Payment existingPayment = getPaymentById(id);

        if (paymentDetails.getPaymentReference() != null) {
            existingPayment.setPaymentReference(paymentDetails.getPaymentReference());
        }
        if (paymentDetails.getAmount() != null) {
            existingPayment.setAmount(paymentDetails.getAmount());
        }
        if (paymentDetails.getPaymentType() != null) {
            existingPayment.setPaymentType(paymentDetails.getPaymentType());
        }
        if (paymentDetails.getTransactionId() != null) {
            existingPayment.setTransactionId(paymentDetails.getTransactionId());
        }
        if (paymentDetails.getPaymentStatus() != null) {
            existingPayment.setPaymentStatus(paymentDetails.getPaymentStatus());
        }
        if (paymentDetails.getPaymentDate() != null) {
            existingPayment.setPaymentDate(paymentDetails.getPaymentDate());
        }
        if (paymentDetails.getDueDate() != null) {
            existingPayment.setDueDate(paymentDetails.getDueDate());
        }
        if (paymentDetails.getNotes() != null) {
            existingPayment.setNotes(paymentDetails.getNotes());
        }

        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(String id) throws ProductException {
        if (!paymentRepository.existsById(id)) {
            throw new ProductException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }
}