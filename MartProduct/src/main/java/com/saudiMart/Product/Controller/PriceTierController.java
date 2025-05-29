package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.PriceTierService;
import com.saudiMart.Product.Utils.ProductException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}