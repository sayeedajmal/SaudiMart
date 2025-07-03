package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.QuoteItem;

public interface QuoteItemRepository extends JpaRepository<QuoteItem, String> {

    Page<QuoteItem> findByQuote(Quote quote, Pageable pageable);

    Page<QuoteItem> findByProduct(Products product, Pageable pageable);

    Page<QuoteItem> findByVariant(ProductVariant variant, Pageable pageable);

    Page<QuoteItem> findByQuoteId(String quoteId, Pageable pageable);

    Page<QuoteItem> findByProductId(String productId, Pageable pageable);

    Page<QuoteItem> findByVariantId(String variantId, Pageable pageable);
}