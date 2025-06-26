package com.saudiMart.Product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Quote.QuoteStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.QuoteService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Quote>>> getAllQuotes() {
        List<Quote> quotes = quoteService.getAllQuotes();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all quotes", quotes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Quote>> getQuoteById(@PathVariable String id) throws ProductException {
        Quote quote = quoteService.getQuoteById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quote", quote));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Quote>> createQuote(@RequestBody Quote quote) throws ProductException {
        Quote createdQuote = quoteService.createQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "Successfully created quote", createdQuote));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Quote>> updateQuote(@PathVariable String id, @RequestBody Quote quoteDetails)
            throws ProductException {
        Quote updatedQuote = quoteService.updateQuote(id, quoteDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated quote", updatedQuote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteQuote(@PathVariable String id) throws ProductException {
        quoteService.deleteQuote(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted quote", null));
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<List<Quote>>> getQuotesByBuyer(@PathVariable Users userId) {
        // Note: Assuming the Users object can be resolved from the path variable
        // based on its ID by Spring or a custom argument resolver.
        // You might need to adjust this based on how user IDs are handled.
        List<Quote> quotes = quoteService.getQuotesByBuyer(userId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quotes by buyer", quotes));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<List<Quote>>> getQuotesBySeller(@PathVariable Users userId) {
        // Note: Assuming the Users object can be resolved from the path variable
        // based on its ID by Spring or a custom argument resolver.
        // You might need to adjust this based on how user IDs are handled.
        List<Quote> quotes = quoteService.getQuotesBySeller(userId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quotes by seller", quotes));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<Quote>>> getQuotesByStatus(@PathVariable QuoteStatus status) {
        List<Quote> quotes = quoteService.getQuotesByStatus(status);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quotes by status", quotes));
    }
}