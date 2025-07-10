package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.Users;

public interface QuoteRepository extends JpaRepository<Quote, String> {
        Page<Quote> findByStatus(QuoteStatus status, Pageable pageable);

        Page<Quote> findBySeller(Users user, Pageable pageable);

        Page<Quote> findByBuyer(Users user, Pageable pageable);

        Page<Quote> findByStatusAndSeller(QuoteStatus status, Users user, Pageable pageable);

        Page<Quote> findByStatusAndBuyer(QuoteStatus status, Users user, Pageable pageable);

        Page<Quote> findBySellerAndBuyer(Users seller, Users buyer, Pageable pageable);

        Page<Quote> findByStatusAndSellerAndBuyer(QuoteStatus status, Users seller, Users buyer, Pageable pageable);

        @Query("SELECT q FROM Quote q JOIN q.quoteItem qi WHERE q.buyer.id = ?1 AND qi.product.id = ?2 AND qi.variant.id = ?3")
        Quote findExistingItem(String buyerId, String productId, String variantId);

        @Query("SELECT q FROM Quote q WHERE (:status IS NULL OR q.status = :status) AND (:seller IS NULL OR q.seller = :seller) AND (:buyer IS NULL OR q.buyer = :buyer)") 
        Page<Quote> searchQuote(@Param("status") QuoteStatus status, @Param("seller") Users seller, @Param("buyer") Users buyer, Pageable pageable);

        Page<Quote> findAll(Specification<Quote> spec, Pageable pageable);
}
