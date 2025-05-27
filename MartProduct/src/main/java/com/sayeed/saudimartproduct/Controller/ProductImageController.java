package com.saudimart.martProduct.Controller;

import com.saudimart.martProduct.Model.ProductImage;
import com.saudimart.martProduct.Model.ResponseWrapper;
import com.saudimart.martProduct.Service.ProductImageService;
import com.saudimart.martProduct.Utils.ProductException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    public ResponseEntity<ResponseWrapper<ProductImage>> getProductImageById(@PathVariable Long id) {
        try {
            ProductImage productImage = productImageService.findProductImageById(id);
            return ResponseWrapper.success(productImage, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error getting product image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> getProductImagesByProductId(@PathVariable Long productId) {
        try {
            List<ProductImage> productImages = productImageService.findProductImagesByProductId(productId);
            return ResponseWrapper.success(productImages, HttpStatus.OK);
        } catch (Exception e) {
            // Consider more specific exception handling if the product ID itself is not found
            return ResponseWrapper.error("Error getting product images for product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/variant/{variantId}")
    @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> getProductImagesByVariantId(@PathVariable Long variantId) {
        try {
            List<ProductImage> productImages = productImageService.findProductImagesByProductVariantId(variantId);
            return ResponseWrapper.success(productImages, HttpStatus.OK);
        } catch (Exception e) {
            // Consider more specific exception handling if the variant ID itself is not found
            return ResponseWrapper.error("Error getting product images for variant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<ProductImage>> createProductImage(@Valid @RequestBody ProductImage productImage) {
        try {
            ProductImage createdProductImage = productImageService.saveProductImage(productImage);
            return ResponseWrapper.success(createdProductImage, HttpStatus.CREATED);
        } catch (ProductException e) {
             return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseWrapper.error("Error creating product image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<ProductImage>> updateProductImage(@PathVariable Long id, @Valid @RequestBody ProductImage productImageDetails) {
        try {
            ProductImage updatedProductImage = productImageService.updateProductImage(id, productImageDetails);
            return ResponseWrapper.success(updatedProductImage, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error updating product image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductImage(@PathVariable Long id) {
        try {
            productImageService.deleteProductImage(id);
            return ResponseWrapper.success(null, HttpStatus.NO_CONTENT);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error deleting product image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleProductException(ProductException ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseWrapper.error(ex.getMessage(), status);
    }

    // Optional: Add a general exception handler for unhandled exceptions
     @ExceptionHandler(Exception.class)
     public ResponseEntity<ResponseWrapper<Void>> handleGeneralException(Exception ex) {
         return ResponseWrapper.error("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
     }
}