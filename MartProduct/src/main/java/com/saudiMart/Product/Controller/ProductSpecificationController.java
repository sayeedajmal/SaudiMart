package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.saudiMart.Product.Utils.ProductException;
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
 public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getAllProductSpecifications() throws ProductException {
        List<ProductSpecification> productSpecifications = productSpecificationService.getAllProductSpecifications();
        return ResponseEntity.ok(new ResponseWrapper<>(productSpecifications, "Product specifications retrieved successfully"));
    }

 @GetMapping("/{id}")
 public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationById(@PathVariable Long id) throws ProductException {
 ProductSpecification productSpecification = productSpecificationService.getProductSpecificationById(id);
 return ResponseEntity.ok(new ResponseWrapper<>(productSpecification, "Product specification retrieved successfully"));
    }

    @PostMapping
 public ResponseEntity<ResponseWrapper<ProductSpecification>> createProductSpecification(@RequestBody ProductSpecification productSpecification) throws ProductException {
        ProductSpecification createdProductSpecification = productSpecificationService.createProductSpecification(productSpecification);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(createdProductSpecification, "Product specification created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> updateProductSpecification(@PathVariable Long id, @RequestBody ProductSpecification productSpecificationDetails) {
        ProductSpecification updatedProductSpecification = productSpecificationService.updateProductSpecification(id, productSpecificationDetails);
 return ResponseEntity.ok(new ResponseWrapper<>(updatedProductSpecification, "Product specification updated successfully"));
    }

    @DeleteMapping("/{id}")
 public ResponseEntity<ResponseWrapper<Void>> deleteProductSpecification(@PathVariable Long id) throws ProductException {
        productSpecificationService.deleteProductSpecification(id);
        return ResponseEntity.ok(new ResponseWrapper<>(null, "Product specification deleted successfully"));
    }
}