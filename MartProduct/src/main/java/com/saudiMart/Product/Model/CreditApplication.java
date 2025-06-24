package com.saudiMart.Product.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "credit_applications")
@Data
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Users buyer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Users seller;

    @NotNull
    @Column(name = "requested_limit", nullable = false, precision = 12, scale = 2)
    private BigDecimal requestedLimit;

    @Column(name = "approved_limit", precision = 12, scale = 2)
    private BigDecimal approvedLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CreditApplicationStatus status = CreditApplicationStatus.PENDING;

    @Column(name = "application_date", updatable = false)
    private LocalDateTime applicationDate;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private Users reviewer;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "on_update", updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        applicationDate = LocalDateTime.now();
    }

    public enum CreditApplicationStatus {
        PENDING, APPROVED, REJECTED, EXPIRED
    }

}