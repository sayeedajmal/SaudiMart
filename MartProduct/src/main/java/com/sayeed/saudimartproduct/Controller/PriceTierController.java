package com.sayeed.saudimartproduct.Controller;

import com.sayeed.saudimartproduct.Model.PriceTier;
import com.sayeed.saudimartproduct.Model.ResponseWrapper;
import com.sayeed.saudimartproduct.Service.PriceTierService;
import com.sayeed.saudimartproduct.Utils.ProductException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price-tiers")
public class PriceTierController {

    private final PriceTierService priceTierService;

    @Autowired
    public PriceTierController(PriceTierService priceTierService) {
        this.priceTierService = priceTierService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<PriceTier>> getPriceTierById(@PathVariable Long id) {
        try {
            PriceTier priceTier = priceTierService.findPriceTierById(id);
            return ResponseWrapper.success(priceTier, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error retrieving price tier: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/variant/{variantId}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getPriceTiersByVariantId(@PathVariable Long variantId) {
        try {
            List<PriceTier> priceTiers = priceTierService.findPriceTiersByProductVariantId(variantId);
            return ResponseWrapper.success(priceTiers, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error retrieving price tiers for variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<PriceTier>> createPriceTier(@Valid @RequestBody PriceTier priceTier) {
        try {
            PriceTier createdPriceTier = priceTierService.savePriceTier(priceTier);
            return ResponseWrapper.success(createdPriceTier, HttpStatus.CREATED);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseWrapper.error("Error creating price tier: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<PriceTier>> updatePriceTier(@PathVariable Long id, @Valid @RequestBody PriceTier priceTierDetails) {
        try {
            PriceTier updatedPriceTier = priceTierService.updatePriceTier(id, priceTierDetails);
            return ResponseWrapper.success(updatedPriceTier, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error updating price tier: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deletePriceTier(@PathVariable Long id) {
        try {
            priceTierService.deletePriceTier(id);
            return ResponseWrapper.success(null, HttpStatus.NO_CONTENT);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error deleting price tier: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleProductException(ProductException ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseWrapper.error(ex.getMessage(), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>> handleGeneralException(Exception ex) {
        return ResponseWrapper.error("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}