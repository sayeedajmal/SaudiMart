package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByStatus(QuoteStatus status);

    List<Quote> findBySeller(Users user);

    List<Quote> findByBuyer(Users user);
}