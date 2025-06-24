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

import com.saudiMart.Product.Model.QuoteItem;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.QuoteItemService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/quoteitems")
public class QuoteItemController {

    @Autowired
    private QuoteItemService quoteItemService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<QuoteItem>>> getAllQuoteItems() {
        try {
            List<QuoteItem> quoteItems = quoteItemService.getAllQuoteItems();
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all quote items.", quoteItems));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<QuoteItem>> getQuoteItemById(@PathVariable Long id) {
        try {
            QuoteItem quoteItem = quoteItemService.getQuoteItemById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quote item.", quoteItem));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<QuoteItem>> createQuoteItem(@RequestBody QuoteItem quoteItem) {
        try {
            QuoteItem createdQuoteItem = quoteItemService.createQuoteItem(quoteItem);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Successfully created quote item.", createdQuoteItem));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<QuoteItem>> updateQuoteItem(@PathVariable Long id,
            @RequestBody QuoteItem quoteItemDetails) {
        try {
            QuoteItem updatedQuoteItem = quoteItemService.updateQuoteItem(id, quoteItemDetails);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated quote item.", updatedQuoteItem));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteQuoteItem(@PathVariable Long id) {
        try {
            quoteItemService.deleteQuoteItem(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted quote item.", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/quote/{quoteId}")
    public ResponseEntity<ResponseWrapper<List<QuoteItem>>> getQuoteItemsByQuoteId(@PathVariable Long quoteId) {
        try {
            List<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByQuoteId(quoteId);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by quote ID.", quoteItems));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<List<QuoteItem>>> getQuoteItemsByProductId(@PathVariable Long productId) {
        try {
            List<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByProductId(productId);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by product ID.", quoteItems));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ResponseWrapper<List<QuoteItem>>> getQuoteItemsByVariantId(@PathVariable Long variantId) {
        try {
            List<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByVariantId(variantId);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by variant ID.", quoteItems));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }
}