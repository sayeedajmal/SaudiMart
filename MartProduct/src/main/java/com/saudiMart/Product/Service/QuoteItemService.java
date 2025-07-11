package com.saudiMart.Product.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.QuoteItem;
import com.saudiMart.Product.Repository.QuoteItemRepository;
import com.saudiMart.Product.Repository.QuoteRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class QuoteItemService {

    @Autowired
    private QuoteItemRepository quoteItemRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    public Page<QuoteItem> getAllQuoteItems(Pageable pageable) {
        return quoteItemRepository.findAll(pageable);
    }

    public QuoteItem getQuoteItemById(String id) throws ProductException {
        return quoteItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Quote Item not found with id: " + id));
    }

    public Page<QuoteItem> searchQuoteItems(String quoteId, String productId, String variantId, Pageable pageable) {
        if (quoteId != null) {
            return quoteItemRepository.findByQuoteId(quoteId, pageable);
        } else if (productId != null) {
            return quoteItemRepository.findByProductId(productId, pageable);
        } else if (variantId != null) {
            return quoteItemRepository.findByVariantId(variantId, pageable);
        }
        return quoteItemRepository.findAll(pageable);
    }

    public QuoteItem createQuoteItem(QuoteItem quoteItem) throws ProductException {
        if (quoteItem == null) {
            throw new ProductException("Quote Item cannot be null");
        }

        String buyerId = quoteItem.getQuote().getBuyer().getId();
        String productId = quoteItem.getProduct().getId();
        String variantId = quoteItem.getVariant() != null ? quoteItem.getVariant().getId() : null;

        QuoteItem existing = quoteRepository.findExistingItem(buyerId, productId, variantId);
        if (existing != null) {
            throw new ProductException("Quote item with same product & variant already exists for this buyer.");
        }

        Quote savedQuote = quoteRepository.save(quoteItem.getQuote());
        quoteItem.setQuote(savedQuote);

        return quoteItemRepository.save(quoteItem);
    }

    public QuoteItem updateQuoteItem(String id, QuoteItem quoteItemDetails) throws ProductException {
        if (quoteItemDetails == null) {
            throw new ProductException("Quote Item details cannot be null for update");
        }
        Optional<QuoteItem> quoteItemOptional = quoteItemRepository.findById(id);
        if (quoteItemOptional.isPresent()) {
            QuoteItem quoteItem = quoteItemOptional.get();
            // Update fields based on quoteItemDetails, handle null checks
            if (quoteItemDetails.getQuantity() != null) {
                quoteItem.setQuantity(quoteItemDetails.getQuantity());
            }
            if (quoteItemDetails.getQuotedPrice() != null) {
                quoteItem.setQuotedPrice(quoteItemDetails.getQuotedPrice());
            }
            if (quoteItemDetails.getDiscountPercent() != null) {
                quoteItem.setDiscountPercent(quoteItemDetails.getDiscountPercent());
            }
            if (quoteItemDetails.getTotalPrice() != null) {
                quoteItem.setTotalPrice(quoteItemDetails.getTotalPrice());
            }

            // Assuming product, variant, and quote are set separately if needed
            // if (quoteItemDetails.getProduct() != null) {
            // quoteItem.setProduct(quoteItemDetails.getProduct());
            // }
            // if (quoteItemDetails.getVariant() != null) {
            // quoteItem.setVariant(quoteItemDetails.getVariant());
            // }
            // if (quoteItemDetails.getQuote() != null) {
            // quoteItem.setQuote(quoteItemDetails.getQuote());
            // }

            return quoteItemRepository.save(quoteItem);
        }
        throw new ProductException("Quote Item not found with id: " + id);
    }

    public void deleteQuoteItem(String id) throws ProductException {
        if (!quoteItemRepository.existsById(id)) {
            throw new ProductException("Quote Item not found with id: " + id);
        }
        quoteItemRepository.deleteById(id);
    }

    public Page<QuoteItem> getQuoteItemsByQuote(Quote quote, Pageable pageable) {
        return quoteItemRepository.findByQuote(quote, pageable);
    }

    public Page<QuoteItem> getQuoteItemsByProduct(Products product, Pageable pageable) {
        return quoteItemRepository.findByProduct(product, pageable);
    }

    public Page<QuoteItem> getQuoteItemsByVariant(ProductVariant variant, Pageable pageable) {
        return quoteItemRepository.findByVariant(variant, pageable);
    }
}