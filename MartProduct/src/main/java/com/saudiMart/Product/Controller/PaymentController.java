package com.saudiMart.Product.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Payment;
import com.saudiMart.Product.Model.Payment.PaymentStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.PaymentService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Payment>>> getAllPayments(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Payment> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all payments", payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Payment>> getPaymentById(@PathVariable String id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved payment", payment));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Payment>> createPayment(@RequestBody Payment payment) {
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(201, "Successfully created payment", createdPayment));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Payment>> updatePayment(@PathVariable String id,
            @RequestBody Payment paymentDetails) {
        try {
            Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated payment", updatedPayment));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deletePayment(@PathVariable String id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted payment", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseWrapper<Page<Payment>>> getPaymentsByOrderId(@PathVariable String orderId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Payment> payments = paymentService.getPaymentsByOrder(orderId, pageable);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved payments by order ID", payments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<Page<Payment>>> getPaymentsByStatus(@PathVariable PaymentStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Payment> payments = paymentService.getPaymentsByPaymentStatus(status, pageable);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved payments by status", payments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<Payment>>> searchPayments(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String paymentReference,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) Payment.PaymentType paymentType,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) Payment.PaymentStatus paymentStatus,
            @RequestParam(required = false) LocalDateTime minPaymentDate,
            @RequestParam(required = false) LocalDateTime maxPaymentDate,
            @RequestParam(required = false) LocalDate minDueDate,
            @RequestParam(required = false) LocalDate maxDueDate,
            @RequestParam(required = false) LocalDateTime minCreatedAt,
            @RequestParam(required = false) LocalDateTime maxCreatedAt,
            @RequestParam(required = false) LocalDateTime minUpdatedAt,
            @RequestParam(required = false) LocalDateTime maxUpdatedAt,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Payment> payments = paymentService.searchPayments(
                    orderId, paymentReference, minAmount, maxAmount, paymentType,
                    transactionId, paymentStatus, minPaymentDate, maxPaymentDate,
                    minDueDate, maxDueDate, minCreatedAt, maxCreatedAt, minUpdatedAt, maxUpdatedAt,
                    pageable);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved payments based on search criteria",
                            payments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "An error occurred during search", null));
        }
    }
}