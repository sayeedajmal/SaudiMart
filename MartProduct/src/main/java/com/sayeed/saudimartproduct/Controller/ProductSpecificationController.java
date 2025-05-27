package com.saudimart.martProduct.Controller;

import com.saudimart.martProduct.Model.ProductSpecification;
import com.saudimart.martProduct.Model.ResponseWrapper;
import com.saudimart.martProduct.Service.ProductSpecificationService;
import com.saudimart.martProduct.Utils.ProductException;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-specifications")
public class ProductSpecificationController {

    private final ProductSpecificationService productSpecificationService;

    public ProductSpecificationController(ProductSpecificationService productSpecificationService) {
        this.productSpecificationService = productSpecificationService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationById(@PathVariable Long id) {
        try {
            ProductSpecification specification = productSpecificationService.findProductSpecificationById(id);
            return ResponseWrapper.success(specification, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error getting product specification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('BUYER', 'SELLER')")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByProductId(@PathVariable Long productId) {
        try {
            List<ProductSpecification> specifications = productSpecificationService.findProductSpecificationsByProductId(productId);
            return ResponseWrapper.success(specifications, HttpStatus.OK);
        } catch (ProductException e) {
             return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST); // Assuming product ID might be invalid
        }
         catch (Exception e) {
            return ResponseWrapper.error("Error getting product specifications by product ID: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> createProductSpecification(@Valid @RequestBody ProductSpecification specification) {
        try {
            ProductSpecification createdSpecification = productSpecificationService.saveProductSpecification(specification);
            return ResponseWrapper.success(createdSpecification, HttpStatus.CREATED);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseWrapper.error("Error creating product specification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> updateProductSpecification(@PathVariable Long id, @Valid @RequestBody ProductSpecification specificationDetails) {
        try {
            ProductSpecification updatedSpecification = productSpecificationService.updateProductSpecification(id, specificationDetails);
            return ResponseWrapper.success(updatedSpecification, HttpStatus.OK);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error updating product specification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductSpecification(@PathVariable Long id) {
        try {
            productSpecificationService.deleteProductSpecification(id);
            return ResponseWrapper.success(null, HttpStatus.NO_CONTENT);
        } catch (ProductException e) {
            return ResponseWrapper.error(e.getMessage(), e.getStatus() != null ? e.getStatus() : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseWrapper.error("Error deleting product specification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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