package com.saudiMart.Product.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.Payment.PaymentStatus;
import com.saudiMart.Product.Repository.PaymentRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(String id) throws ProductException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ProductException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByOrder(Order order) {
        return paymentRepository.findByOrder(order);
    }

    public List<Payment> getPaymentsByPaymentStatus(PaymentStatus status) {
        return paymentRepository.findByPaymentStatus(status);
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