package com.saudiMart.Product.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.Payment.PaymentStatus;
import com.saudiMart.Product.Model.Payment.PaymentType;
import com.saudiMart.Product.Repository.OrderRepository;
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

    public Page<Payment> searchPayments(String orderId, String paymentReference, BigDecimal minAmount,
            BigDecimal maxAmount, PaymentType paymentType, String transactionId, PaymentStatus paymentStatus,
            LocalDateTime minPaymentDate, LocalDateTime maxPaymentDate, LocalDate minDueDate, LocalDate maxDueDate,
            LocalDateTime minCreatedAt, LocalDateTime maxCreatedAt, LocalDateTime minUpdatedAt,
            LocalDateTime maxUpdatedAt, Pageable pageable) throws ProductException {
        Specification<Payment> spec = Specification.where(null);

        if (orderId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ProductException("Order not found with id: " + orderId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("order"), order));
        }
        if (paymentReference != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentReference"), paymentReference));
        }
        if (minAmount != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
        }
        if (maxAmount != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
        }
        if (paymentType != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentType"), paymentType));
        }
        if (transactionId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("transactionId"), transactionId));
        }
        if (paymentStatus != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentStatus"), paymentStatus));
        }
        if (minPaymentDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("paymentDate"), minPaymentDate));
        }
        if (maxPaymentDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("paymentDate"), maxPaymentDate));
        }
        if (minDueDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dueDate"), minDueDate));
        }
        if (maxDueDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), maxDueDate));
        }
        if (minCreatedAt != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), minCreatedAt));
        }
        if (maxCreatedAt != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), maxCreatedAt));
        }
        if (minUpdatedAt != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("updatedAt"), minUpdatedAt));
        }
        if (maxUpdatedAt != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("updatedAt"), maxUpdatedAt));
        }
        return paymentRepository.findAll(spec, pageable);
    }
}