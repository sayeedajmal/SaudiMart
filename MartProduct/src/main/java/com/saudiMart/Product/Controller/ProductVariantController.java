package com.saudiMart.Product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.saudiMart.Product.Utils.ProductException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @Autowired
    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

 @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
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

 @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductVariant>>> getAllProductVariants() {
        List<ProductVariant> productVariants = productVariantService.findAllProductVariants();
        return ResponseWrapper.success(productVariants, HttpStatus.OK);
    }

 @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
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

 @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
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

 @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
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

 @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
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