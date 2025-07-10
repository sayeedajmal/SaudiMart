package com.saudiMart.Product.Service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Repository.QuoteRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ProductsRepository productsRepository;

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

        String buyerId = quote.getBuyer().getId();
        String productId = quote.getQuoteItem().getProduct().getId();
        String variantId = quote.getQuoteItem().getVariant() != null ? quote.getQuoteItem().getVariant().getId() : null;

        Quote existing = quoteRepository.findExistingItem(buyerId, productId, variantId);
        if (existing != null && existing.getStatus() != QuoteStatus.REJECTED) {
            throw new ProductException("Quote item with same product & variant already exists for You.");
        }

        Optional<Products> optionalProduct = productsRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductException("Product not found");
        }

        Products product = optionalProduct.get();

        ProductVariant matchedVariant = product.getVariants().stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new ProductException("Product variant not found"));

        BigDecimal price = matchedVariant.getBasePrice();
        int quantity = quote.getQuoteItem().getQuantity();

        BigDecimal quotedPrice = price;
        BigDecimal subtotal = quotedPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = subtotal.add(taxAmount);

        quote.getQuoteItem().setQuotedPrice(quotedPrice);
        quote.getQuoteItem().setTotalPrice(subtotal);
        quote.getQuoteItem().setDiscountPercent(BigDecimal.ZERO);

        quote.setSubtotal(subtotal);
        quote.setTaxAmount(taxAmount);
        quote.setTotalAmount(totalAmount);

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

    public class QuoteSpecifications {

        public static Specification<Quote> hasStatus(QuoteStatus status) {
            return (root, query, builder) -> builder.equal(root.get("status"), status);
        }

        public static Specification<Quote> hasBuyer(Users buyer) {
            return (root, query, builder) -> builder.equal(root.get("buyer"), buyer);
        }

        public static Specification<Quote> hasSeller(Users seller) {
            return (root, query, builder) -> builder.equal(root.get("seller"), seller);
        }
    }

    public Page<Quote> searchQuotes(QuoteStatus status, Users buyer, Users seller, Pageable pageable) {
        Specification<Quote> spec = Specification.where(null);

        if (status != null) {
            spec = spec.and(QuoteSpecifications.hasStatus(status));
        }

        if (buyer != null) {
            spec = spec.and(QuoteSpecifications.hasBuyer(buyer));
        }

        if (seller != null) {
            spec = spec.and(QuoteSpecifications.hasSeller(seller));
        }

        return quoteRepository.findAll(spec, pageable);
    }
}