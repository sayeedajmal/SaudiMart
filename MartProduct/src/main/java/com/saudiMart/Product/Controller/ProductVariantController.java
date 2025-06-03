package com.saudiMart.Product.Controller;

import java.util.List;
import java.util.Optional;

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

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductVariantService;

@RestController
@RequestMapping("/api/productvariants")
public class ProductVariantController {

   private final ProductVariantService productVariantService;

   @Autowired
   public ProductVariantController(ProductVariantService productVariantService) {
      this.productVariantService = productVariantService;
   }

   @GetMapping
   public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getAllProductVariants() {
      List<ProductVariant> productVariants = productVariantService.getAllProductVariants();
      ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
            "Product variants retrieved successfully", productVariants);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/product/{productId}")
   public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getProductVariantsByProductId(
         @PathVariable Long productId) {
      List<ProductVariant> productVariants = productVariantService.getProductVariantsByProductId(productId);
      ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
            "Product variants by product ID retrieved successfully", productVariants);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ResponseWrapper<ProductVariant>> getProductVariantById(@PathVariable Long id) {
      Optional<ProductVariant> productVariant = productVariantService.getProductVariantById(id);
      if (productVariant.isPresent()) {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.OK.value(),
               "Product variant retrieved successfully", productVariant.get());
         return ResponseEntity.ok(response);
      } else {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
               "Product variant not found", null);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
   }

   @PostMapping
   public ResponseEntity<ResponseWrapper<ProductVariant>> createProductVariant(
         @RequestBody ProductVariant productVariant) {
      ProductVariant createdProductVariant = productVariantService.createProductVariant(productVariant);
      ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.CREATED.value(),
            "Product variant created successfully", createdProductVariant);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PutMapping("/{id}")
   public ResponseEntity<ResponseWrapper<ProductVariant>> updateProductVariant(@PathVariable Long id,
         @RequestBody ProductVariant productVariantDetails) {
      try {
         ProductVariant updatedProductVariant = productVariantService.updateProductVariant(id,
               productVariantDetails);
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.OK.value(),
               "Product variant updated successfully", updatedProductVariant);
         return ResponseEntity.ok(response);
      } catch (RuntimeException e) {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
               e.getMessage(), null);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<ResponseWrapper<Void>> deleteProductVariant(@PathVariable Long id) {
      try {
         productVariantService.deleteProductVariant(id);
         ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.NO_CONTENT.value(),
               "Product variant deleted successfully", null);
         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
      } catch (RuntimeException e) {
         ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
   }

   @GetMapping("/by-variant-id/{variantId}")
   public ResponseEntity<ResponseWrapper<ProductVariant>> findProductVariantByVariantId(@PathVariable Long variantId) {
      ProductVariant productVariant = productVariantService.findProductVariantByVariantId(variantId);
      if (productVariant != null) {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.OK.value(),
               "Product variant retrieved successfully", productVariant);
         return ResponseEntity.ok(response);
      } else {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
               "Product variant not found with variant ID: " + variantId, null);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
   }

   @GetMapping("/by-sku")
   public ResponseEntity<ResponseWrapper<ProductVariant>> findProductVariantBySku(@RequestBody String sku) {
      ProductVariant productVariant = productVariantService.findProductVariantBySku(sku);
      if (productVariant != null) {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.OK.value(),
               "Product variant retrieved successfully", productVariant);
         return ResponseEntity.ok(response);
      } else {
         ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
               "Product variant not found with SKU: " + sku, null);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
   }

   @GetMapping("/by-variant-name")
   public ResponseEntity<ResponseWrapper<List<ProductVariant>>> findProductVariantsByVariantName(
         @RequestBody String variantName) {
      List<ProductVariant> productVariants = productVariantService.findProductVariantsByVariantName(variantName);
      ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
            "Product variants retrieved successfully", productVariants);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/available/{available}")
   public ResponseEntity<ResponseWrapper<List<ProductVariant>>> findProductVariantsByAvailable(
         @PathVariable Boolean available) {
      List<ProductVariant> productVariants = productVariantService.findProductVariantsByAvailable(available);
      ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
            "Product variants retrieved successfully", productVariants);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/by-product-id/{productId}/available/{available}")
   public ResponseEntity<ResponseWrapper<List<ProductVariant>>> findProductVariantsByProductIdAndAvailable(
         @PathVariable Long productId,
         @PathVariable Boolean available) {
      List<ProductVariant> productVariants = productVariantService.findProductVariantsByProductIdAndAvailable(productId,
            available);
      ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
            "Product variants retrieved successfully", productVariants);
      return ResponseEntity.ok(response);
   }

}
