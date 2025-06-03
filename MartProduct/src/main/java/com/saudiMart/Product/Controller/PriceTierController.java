package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.PriceTierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pricetiers")
public class PriceTierController {

    @Autowired
    private PriceTierService priceTierService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<PriceTier>>> getAllPriceTiers() {
        List<PriceTier> priceTiers = priceTierService.getAllPriceTiers();
        ResponseWrapper<List<PriceTier>> response = new ResponseWrapper<>(true, "Successfully retrieved all price tiers", priceTiers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PriceTier>> getPriceTierById(@PathVariable Long id) {
        Optional<PriceTier> priceTierOptional = priceTierService.getPriceTierById(id);
        if (priceTierOptional.isPresent()) {
            ResponseWrapper<PriceTier> response = new ResponseWrapper<>(true, "Successfully retrieved price tier", priceTierOptional.get());
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<PriceTier> response = new ResponseWrapper<>(false, "Price tier not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<PriceTier>> createPriceTier(@RequestBody PriceTier priceTier) {
        PriceTier createdPriceTier = priceTierService.createPriceTier(priceTier);
        ResponseWrapper<PriceTier> response = new ResponseWrapper<>(true, "Successfully created price tier", createdPriceTier);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PriceTier>> updatePriceTier(@PathVariable Long id, @RequestBody PriceTier priceTierDetails) {
        PriceTier updatedPriceTier = priceTierService.updatePriceTier(id, priceTierDetails);
        if (updatedPriceTier != null) {
            ResponseWrapper<PriceTier> response = new ResponseWrapper<>(true, "Successfully updated price tier", updatedPriceTier);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<PriceTier> response = new ResponseWrapper<>(false, "Price tier not found for update", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deletePriceTier(@PathVariable Long id) {
        priceTierService.deletePriceTier(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>(true, "Successfully deleted price tier", null);
        return ResponseEntity.ok(response);
    }
}