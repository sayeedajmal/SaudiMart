package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productvariants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getAllProductVariants() {
        List<ProductVariant> productVariants = productVariantService.getAllProductVariants();
        ResponseWrapper<List<ProductVariant>> response = new ResponseWrapper<>(true, "Successfully retrieved all product variants", productVariants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> getProductVariantById(@PathVariable Long id) {
        Optional<ProductVariant> productVariant = productVariantService.getProductVariantById(id);
        if (productVariant.isPresent()) {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(true, "Successfully retrieved product variant", productVariant.get());
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(false, "Product variant not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductVariant>> createProductVariant(@RequestBody ProductVariant productVariant) {
        ProductVariant createdProductVariant = productVariantService.createProductVariant(productVariant);
        ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(true, "Successfully created product variant", createdProductVariant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> updateProductVariant(@PathVariable Long id, @RequestBody ProductVariant productVariantDetails) {
        ProductVariant updatedProductVariant = productVariantService.updateProductVariant(id, productVariantDetails);
        if (updatedProductVariant != null) {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(true, "Successfully updated product variant", updatedProductVariant);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(false, "Product variant not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductVariant(@PathVariable Long id) {
        productVariantService.deleteProductVariant(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>(true, "Successfully deleted product variant", null);
        return ResponseEntity.ok(response);
    }
}