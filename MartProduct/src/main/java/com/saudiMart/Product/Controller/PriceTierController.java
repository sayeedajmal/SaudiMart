package com.saudiMart.Product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.PriceTierService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/pricetiers")
public class PriceTierController {

   @Autowired
   private PriceTierService priceTierService;

   @GetMapping
   public ResponseEntity<ResponseWrapper<List<PriceTier>>> getAllPriceTiers() throws ProductException {
      List<PriceTier> priceTiers = priceTierService.getAllPriceTiers();
      return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all price tiers", priceTiers));
   }

   @GetMapping("/{id}")
   public ResponseEntity<ResponseWrapper<PriceTier>> getPriceTierById(@PathVariable String id) throws ProductException {
      PriceTier priceTier = priceTierService.getPriceTierById(id);
      return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved price tier", priceTier));
   }

   @PostMapping
   public ResponseEntity<ResponseWrapper<PriceTier>> createPriceTier(@RequestBody PriceTier priceTier)
         throws ProductException {
      PriceTier createdPriceTier = priceTierService.createPriceTier(priceTier);
      return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully created price tier", createdPriceTier));
   }

   @PutMapping("/{id}")
   public ResponseEntity<ResponseWrapper<PriceTier>> updatePriceTier(@PathVariable String id,
         @RequestBody PriceTier priceTierDetails) throws ProductException {
      PriceTier updatedPriceTier = priceTierService.updatePriceTier(id, priceTierDetails);
      return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated price tier", updatedPriceTier));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<ResponseWrapper<Void>> deletePriceTier(@PathVariable String id) throws ProductException {
      priceTierService.deletePriceTier(id);
      return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted price tier", null));
   }
}