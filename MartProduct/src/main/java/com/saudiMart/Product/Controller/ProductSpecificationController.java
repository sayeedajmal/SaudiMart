package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productSpecifications")
public class ProductSpecificationController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getAllProductSpecifications() {
        List<ProductSpecification> productSpecifications = productSpecificationService.getAllProductSpecifications();
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved successfully", productSpecifications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationById(@PathVariable Long id) {
        Optional<ProductSpecification> productSpecification = productSpecificationService.getProductSpecificationById(id);
        if (productSpecification.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specification retrieved successfully", productSpecification.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product specification not found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductSpecification>> createProductSpecification(@RequestBody ProductSpecification productSpecification) {
        ProductSpecification createdProductSpecification = productSpecificationService.createProductSpecification(productSpecification);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Product specification created successfully", createdProductSpecification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> updateProductSpecification(@PathVariable Long id, @RequestBody ProductSpecification productSpecificationDetails) {
        Optional<ProductSpecification> updatedProductSpecification = Optional.ofNullable(productSpecificationService.updateProductSpecification(id, productSpecificationDetails));
        if (updatedProductSpecification.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specification updated successfully", updatedProductSpecification.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product specification not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductSpecification(@PathVariable Long id) {
        boolean deleted = productSpecificationService.deleteProductSpecification(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specification deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product specification not found", null));
        }
    }
}