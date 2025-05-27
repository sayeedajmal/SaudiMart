package com.sayeed.saudimartproduct.Controller;

import com.sayeed.saudimartproduct.Model.ProductSpecification;
import com.sayeed.saudimartproduct.Model.ResponseWrapper;
import com.sayeed.saudimartproduct.Service.ProductSpecificationService;
import com.sayeed.saudimartproduct.Utils.ProductException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-specifications")
public class ProductSpecificationController {

    private final ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductSpecificationController(ProductSpecificationService productSpecificationService) {
        this.productSpecificationService = productSpecificationService;
    }

    @GetMapping("/{id}")
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