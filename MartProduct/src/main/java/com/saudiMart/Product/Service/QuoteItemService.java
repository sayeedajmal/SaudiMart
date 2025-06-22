package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.QuoteItem;
import com.saudiMart.Product.Repository.QuoteItemRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class QuoteItemService {

    @Autowired
    private QuoteItemRepository quoteItemRepository;

    public List<QuoteItem> getAllQuoteItems() {
        return quoteItemRepository.findAll();
    }

    public QuoteItem getQuoteItemById(Long id) throws ProductException {
        return quoteItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Quote Item not found with id: " + id));
    }

    public List<QuoteItem> getQuoteItemsByQuoteId(Long quoteId) {
        return quoteItemRepository.findByQuoteId(quoteId);
    }

    public List<QuoteItem> getQuoteItemsByProductId(Long productId) {
        return quoteItemRepository.findByProductId(productId);
    }

    public List<QuoteItem> getQuoteItemsByVariantId(Long variantId) {
        return quoteItemRepository.findByVariantId(variantId);
    }


    public QuoteItem createQuoteItem(QuoteItem quoteItem) throws ProductException {
        if (quoteItem == null) {
            throw new ProductException("Quote Item cannot be null");
        }
        return quoteItemRepository.save(quoteItem);
    }

    public QuoteItem updateQuoteItem(Long id, QuoteItem quoteItemDetails) throws ProductException {
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
            //     quoteItem.setProduct(quoteItemDetails.getProduct());
            // }
            // if (quoteItemDetails.getVariant() != null) {
            //     quoteItem.setVariant(quoteItemDetails.getVariant());
            // }
            // if (quoteItemDetails.getQuote() != null) {
            //     quoteItem.setQuote(quoteItemDetails.getQuote());
            // }


            return quoteItemRepository.save(quoteItem);
        }
        throw new ProductException("Quote Item not found with id: " + id);
    }

    public void deleteQuoteItem(Long id) throws ProductException {
        if (!quoteItemRepository.existsById(id)) {
            throw new ProductException("Quote Item not found with id: " + id);
        }
        quoteItemRepository.deleteById(id);
    }
}