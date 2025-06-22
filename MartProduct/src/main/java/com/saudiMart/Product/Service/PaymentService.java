package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.PaymentStatus; // Assuming PaymentStatus enum exists
import com.saudiMart.Product.Repository.PaymentRepository;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) throws ProductException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ProductException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
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

    public Payment updatePayment(Long id, Payment paymentDetails) throws ProductException {
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

    public void deletePayment(Long id) throws ProductException {
        if (!paymentRepository.existsById(id)) {
            throw new ProductException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }
}