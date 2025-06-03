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
@RequestMapping("/productspecifications")
public class ProductSpecificationController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getAllProductSpecifications() {
        List<ProductSpecification> productSpecifications = productSpecificationService.getAllProductSpecifications();
        return ResponseEntity.ok(new ResponseWrapper<>(productSpecifications, "Product specifications retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationById(@PathVariable Long id) {
        Optional<ProductSpecification> productSpecification = productSpecificationService.getProductSpecificationById(id);
        if (productSpecification.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(productSpecification.get(), "Product specification retrieved successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product specification not found"));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductSpecification>> createProductSpecification(@RequestBody ProductSpecification productSpecification) {
        ProductSpecification createdProductSpecification = productSpecificationService.createProductSpecification(productSpecification);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(createdProductSpecification, "Product specification created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> updateProductSpecification(@PathVariable Long id, @RequestBody ProductSpecification productSpecificationDetails) {
        ProductSpecification updatedProductSpecification = productSpecificationService.updateProductSpecification(id, productSpecificationDetails);
        if (updatedProductSpecification != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(updatedProductSpecification, "Product specification updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product specification not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductSpecification(@PathVariable Long id) {
        productSpecificationService.deleteProductSpecification(id);
        return ResponseEntity.ok(new ResponseWrapper<>(null, "Product specification deleted successfully"));
    }
}