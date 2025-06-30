package com.saudiMart.Product.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.QuoteRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public Page<Quote> getAllQuotes(Pageable pageable) {
        return quoteRepository.findAll(pageable);
    }

    public Quote getQuoteById(String id) throws ProductException {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new ProductException("Quote not found with id: " + id));
    }

    public Quote createQuote(Quote quote) throws ProductException {
        if (quote == null) {
            throw new ProductException("Quote cannot be null");
        }
        // Add any business logic or validation before saving
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(String id, Quote quoteDetails) throws ProductException {
        if (quoteDetails == null) {
            throw new ProductException("Quote details cannot be null for update");
        }
        Optional<Quote> quoteOptional = quoteRepository.findById(id);
        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();

            if (quoteDetails.getQuoteNumber() != null)
                quote.setQuoteNumber(quoteDetails.getQuoteNumber());
            if (quoteDetails.getBuyer() != null)
                quote.setBuyer(quoteDetails.getBuyer());
            if (quoteDetails.getSeller() != null)
                quote.setSeller(quoteDetails.getSeller());
            if (quoteDetails.getStatus() != null)
                quote.setStatus(quoteDetails.getStatus());
            if (quoteDetails.getValidUntil() != null)
                quote.setValidUntil(quoteDetails.getValidUntil());
            if (quoteDetails.getSubtotal() != null)
                quote.setSubtotal(quoteDetails.getSubtotal());
            if (quoteDetails.getTaxAmount() != null)
                quote.setTaxAmount(quoteDetails.getTaxAmount());
            if (quoteDetails.getTotalAmount() != null)
                quote.setTotalAmount(quoteDetails.getTotalAmount());
            if (quoteDetails.getNotes() != null)
                quote.setNotes(quoteDetails.getNotes());

            // Update timestamps in @PreUpdate in the entity

            return quoteRepository.save(quote);
        }
        throw new ProductException("Quote not found with id: " + id);
    }

    public void deleteQuote(String id) throws ProductException {
        if (!quoteRepository.existsById(id)) {
            throw new ProductException("Quote not found with id: " + id);
        }
        quoteRepository.deleteById(id);
    }

    public Page<Quote> getQuotesByBuyer(Users user, Pageable pageable) {
        return quoteRepository.findByBuyer(user, pageable);
    }

    public Page<Quote> getQuotesBySeller(Users user, Pageable pageable) {
        return quoteRepository.findBySeller(user, pageable);
    }

    public Page<Quote> getQuotesByStatus(QuoteStatus status, Pageable pageable) {
        return quoteRepository.findByStatus(status, pageable);
    }

    public Page<Quote> searchQuotes(QuoteStatus status, Users seller, Users buyer, Pageable pageable) {
        return quoteRepository.searchQuotes(status, seller, buyer, pageable);
    }
}