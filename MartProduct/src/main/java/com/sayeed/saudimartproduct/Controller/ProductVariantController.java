package com.sayeed.saudimartproduct.Controller;

import com.sayeed.saudimartproduct.Model.ProductVariant;
import com.sayeed.saudimartproduct.Model.ResponseWrapper;
import com.sayeed.saudimartproduct.Service.ProductVariantService;
import com.sayeed.saudimartproduct.Utils.ProductException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @Autowired
    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> getProductVariantById(@PathVariable Long id) {
        try {
            ProductVariant productVariant = productVariantService.findProductVariantById(id);
            return ResponseWrapper.success(productVariant, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error fetching product variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getAllProductVariants() {
        List<ProductVariant> productVariants = productVariantService.findAllProductVariants();
        return ResponseWrapper.success(productVariants, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getProductVariantsByProductId(@PathVariable Long productId) {
        try {
            List<ProductVariant> productVariants = productVariantService.findProductVariantsByProductId(productId);
            return ResponseWrapper.success(productVariants, HttpStatus.OK);
        } catch (ProductException e) {
             return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
             return ResponseWrapper.error("Error fetching product variants by product ID: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductVariant>> createProductVariant(@Valid @RequestBody ProductVariant productVariant) {
        try {
            ProductVariant createdProductVariant = productVariantService.saveProductVariant(productVariant);
            return ResponseWrapper.success(createdProductVariant, HttpStatus.CREATED);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseWrapper.error("Error creating product variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> updateProductVariant(@PathVariable Long id, @Valid @RequestBody ProductVariant productVariantDetails) {
        try {
            ProductVariant updatedProductVariant = productVariantService.updateProductVariant(id, productVariantDetails);
            return ResponseWrapper.success(updatedProductVariant, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error updating product variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductVariant(@PathVariable Long id) {
        try {
            productVariantService.deleteProductVariant(id);
            return ResponseWrapper.success(null, HttpStatus.NO_CONTENT);
        } catch (ProductException e) {
             return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error deleting product variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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