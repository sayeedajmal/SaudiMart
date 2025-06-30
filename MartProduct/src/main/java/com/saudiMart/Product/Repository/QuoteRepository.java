package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.Users;

public interface QuoteRepository extends JpaRepository<Quote, String> {
    Page<Quote> findByStatus(QuoteStatus status, Pageable pageable);

    Page<Quote> findBySeller(Users user, Pageable pageable);

    Page<Quote> findByBuyer(Users user, Pageable pageable);

    @Query("SELECT q FROM Quote q WHERE " +
            "(:status is null or q.status = :status) and " +
            "(:seller is null or q.seller = :seller) and " +
            "(:buyer is null or q.buyer = :buyer)")
    Page<Quote> searchQuotes(QuoteStatus status, Users seller, Users buyer, Pageable pageable);
}