package com.saudiMart.Product.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "quotes", indexes = {
        @Index(name = "idx_quotes_buyer_seller", columnList = "buyer_id, seller_id"),
        @Index(name = "idx_quotes_status", columnList = "status")
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = "quote_number")
})
@Data
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quote_number", length = 50, unique = true)
    private String quoteNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Users buyer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Users seller;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QuoteStatus status = QuoteStatus.DRAFT;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // Consider generating quoteNumber here if not handled by the database default
        // e.g., if using a different database or need application-level generation
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum QuoteStatus {
        DRAFT, SENT, ACCEPTED, REJECTED, EXPIRED
    }
}