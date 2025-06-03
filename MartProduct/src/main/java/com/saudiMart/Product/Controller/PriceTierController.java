package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.PriceTierService;
import com.saudiMart.Product.Utils.ProductException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/price-tiers")
public class PriceTierController {

    @Autowired
    private PriceTierService priceTierService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getAllPriceTiers() {
        List<PriceTier> priceTiers = priceTierService.getAllPriceTiers();
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully", priceTiers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PriceTier>> getPriceTierById(@PathVariable Long id) {
        Optional<PriceTier> priceTier = priceTierService.getPriceTierById(id);
        return priceTier.map(tier -> ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier retrieved successfully", tier)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Price tier not found", null)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<PriceTier>> createPriceTier(@RequestBody PriceTier priceTier) {
        PriceTier createdPriceTier = priceTierService.createPriceTier(priceTier);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Price tier created successfully", createdPriceTier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PriceTier>> updatePriceTier(@PathVariable Long id, @RequestBody PriceTier priceTierDetails) throws ProductException {
        try {
            PriceTier updatedPriceTier = priceTierService.updatePriceTier(id, priceTierDetails);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier updated successfully", updatedPriceTier));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deletePriceTier(@PathVariable Long id) throws ProductException {
        try {
            priceTierService.deletePriceTier(id);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/by-tier-id/{tierId}")
    public ResponseEntity<ResponseWrapper<Optional<PriceTier>>> getPriceTierByTierId(@PathVariable Long tierId) {
        Optional<PriceTier> priceTier = priceTierService.findPriceTierByTierId(tierId);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier retrieved successfully by tier ID", priceTier));
    }

    @GetMapping("/by-product")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByProduct(@RequestBody Products product) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByProduct(product);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by product", priceTiers));
    }

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByProductId(@PathVariable Long productId) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByProductId(productId);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by product ID", priceTiers));
    }

    @GetMapping("/by-min-quantity/{minimumQuantity}")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByMinimumQuantity(@PathVariable Integer minimumQuantity) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByMinimumQuantity(minimumQuantity);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by minimum quantity", priceTiers));
    }

    @GetMapping("/by-price/{pricePerUnit}")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByPricePerUnit(@PathVariable BigDecimal pricePerUnit) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByPricePerUnit(pricePerUnit);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by price per unit", priceTiers));
    }

    @GetMapping("/by-max-quantity/{maxQuantity}")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByMaxQuantity(@PathVariable Integer maxQuantity) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByMaxQuantity(maxQuantity);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by maximum quantity", priceTiers));
    }

    @GetMapping("/active/{isActive}")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByIsActive(@PathVariable Boolean isActive) {
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByIsActive(isActive);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by active status", priceTiers));
    }

    @GetMapping("/created-between")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByCreatedAtBetween(@RequestBody LocalDateTime[] dates) {
        if (dates == null || dates.length != 2) {
            return ResponseEntity.badRequest().body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), "Please provide a start and end date", null));
        }
        LocalDateTime start = dates[0];
        LocalDateTime end = dates[1];
        List<PriceTier> priceTiers = priceTierService.findPriceTiersByCreatedAtBetween(start, end);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tiers retrieved successfully by creation date range", priceTiers));
    }

    @GetMapping("/by-product-and-quantity")
    public ResponseEntity<ResponseWrapper<Optional<PriceTier>>> getPriceTierByProductAndQuantity(@RequestBody ProductAndQuantityRequest request) {
        Optional<PriceTier> priceTier = priceTierService.findPriceTierByProductAndQuantityRange(request.getProduct(), request.getQuantity());
        return priceTier.map(tier -> ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier retrieved successfully by product and quantity", tier)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Price tier not found for product and quantity", null)));
    }

    @GetMapping("/by-product-id-and-quantity/{productId}/{quantity}")
    public ResponseEntity<ResponseWrapper<Optional<PriceTier>>> getPriceTierByProductIdAndQuantity(@PathVariable Long productId, @PathVariable Integer quantity) {
        Optional<PriceTier> priceTier = priceTierService.findPriceTierByProductIdAndQuantityRange(productId, quantity);
        return priceTier.map(tier -> ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Price tier retrieved successfully by product ID and quantity", tier)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Price tier not found for product ID and quantity", null)));
    }
}

// Helper class for the /by-product-and-quantity endpoint
class ProductAndQuantityRequest {
    private Products product;
    private Integer quantity;
    public Products getProduct() { return product; }
    public void setProduct(Products product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}