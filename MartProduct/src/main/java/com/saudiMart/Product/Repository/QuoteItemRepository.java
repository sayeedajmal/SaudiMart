package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.QuoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {

    List<QuoteItem> findByQuote(Quote quote);

    List<QuoteItem> findByProduct(Products product);

    List<QuoteItem> findByVariant(ProductVariant variant);

    List<QuoteItem> findByQuoteId(Long quoteId);

    List<QuoteItem> findByProductId(Long productId);

    List<QuoteItem> findByVariantId(Long variantId);
}