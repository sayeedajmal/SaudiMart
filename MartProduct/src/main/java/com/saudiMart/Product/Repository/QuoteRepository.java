package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByBuyerId(Long buyerId);
    List<Quote> findBySellerId(Long sellerId);
}