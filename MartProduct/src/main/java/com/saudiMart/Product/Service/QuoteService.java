package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Repository.QuoteRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(Long id) throws ProductException {
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

    public Quote updateQuote(Long id, Quote quoteDetails) throws ProductException {
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

    public void deleteQuote(Long id) throws ProductException {
        if (!quoteRepository.existsById(id)) {
            throw new ProductException("Quote not found with id: " + id);
        }
        quoteRepository.deleteById(id);
    }

    public List<Quote> getQuotesByBuyer(Users user) {
        return quoteRepository.findByBuyer(user);
    }

    public List<Quote> getQuotesBySeller(Users user) {
        return quoteRepository.findBySeller(user);
    }

    public List<Quote> getQuotesByStatus(QuoteStatus status) {
        return quoteRepository.findByStatus(status);
    }
}