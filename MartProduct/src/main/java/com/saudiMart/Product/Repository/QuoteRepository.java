package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.QuoteItem;
import com.saudiMart.Product.Model.Users;

public interface QuoteRepository extends JpaRepository<Quote, String> {
    Page<Quote> findByStatus(QuoteStatus status, Pageable pageable);

    Page<Quote> findBySeller(Users user, Pageable pageable);

    Page<Quote> findByBuyer(Users user, Pageable pageable);

    Page<Quote> findByStatusAndSeller(QuoteStatus status, Users user, Pageable pageable);

    Page<Quote> findByStatusAndBuyer(QuoteStatus status, Users user, Pageable pageable);

    Page<Quote> findBySellerAndBuyer(Users seller, Users buyer, Pageable pageable);

    Page<Quote> findByStatusAndSellerAndBuyer(QuoteStatus status, Users seller, Users buyer, Pageable pageable);

    @Query("SELECT qi FROM QuoteItem qi " +
            "WHERE qi.quote.buyer.id = :buyerId " +
            "AND qi.product.id = :productId " +
            "AND (:variantId IS NULL OR qi.variant.id = :variantId)")
    QuoteItem findExistingItem(String buyerId, String productId, String variantId);

    @Query("SELECT q FROM Quote q WHERE " +
            "(:status is null or q.status = :status) and " +
            "(:seller is null or q.seller = :seller) and " +
            "(:buyer is null or q.buyer = :buyer)")
    Page<Quote> searchQuotes(QuoteStatus status, Users seller, Users buyer, Pageable pageable);
}