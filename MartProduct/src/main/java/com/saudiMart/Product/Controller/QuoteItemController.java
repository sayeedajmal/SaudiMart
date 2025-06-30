package com.saudiMart.Product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Quote;
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
    public ResponseEntity<ResponseWrapper<Page<QuoteItem>>> getAllQuoteItems(
            @RequestParam(required = false) String quoteId,
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String variantId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<QuoteItem> quoteItems = quoteItemService.searchQuoteItems(quoteId, productId, variantId, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved quote items based on criteria.", quoteItems));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<QuoteItem>> getQuoteItemById(@PathVariable String id) {
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
                    .body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Successfully created quote item.",
                            createdQuoteItem));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<QuoteItem>> updateQuoteItem(@PathVariable String id,
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
    public ResponseEntity<ResponseWrapper<Void>> deleteQuoteItem(@PathVariable String id) {
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
    public ResponseEntity<ResponseWrapper<Page<QuoteItem>>> getQuoteItemsByQuoteId(@RequestBody Quote quote,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Page<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByQuote(quote, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by quote ID.", quoteItems));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<Page<QuoteItem>>> getQuoteItemsByProductId(@RequestBody Products product,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Page<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByProduct(product, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by product ID.", quoteItems));
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ResponseWrapper<Page<QuoteItem>>> getQuoteItemsByVariant(@RequestBody ProductVariant variant,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Page<QuoteItem> quoteItems = quoteItemService.getQuoteItemsByVariant(variant, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved quote items by variant ID.", quoteItems));
    }
}