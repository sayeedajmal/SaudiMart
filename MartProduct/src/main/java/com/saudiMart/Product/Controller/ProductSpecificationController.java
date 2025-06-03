package com.saudiMart.Product.Controller;

import java.util.List;

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

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductSpecificationService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/productspecifications")
public class ProductSpecificationController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getAllProductSpecifications()
            throws ProductException {
        List<ProductSpecification> productSpecifications = productSpecificationService.getAllProductSpecifications();
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Product specifications retrieved successfully", productSpecifications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationById(@PathVariable Long id)
            throws ProductException {
        ProductSpecification productSpecification = productSpecificationService.getProductSpecificationById(id);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Product specification retrieved successfully", productSpecification));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductSpecification>> createProductSpecification(
            @RequestBody ProductSpecification productSpecification) throws ProductException {
        ProductSpecification createdProductSpecification = productSpecificationService
                .createProductSpecification(productSpecification);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper<>(200, "Product specification created successfully", createdProductSpecification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> updateProductSpecification(@PathVariable Long id,
            @RequestBody ProductSpecification productSpecificationDetails) throws ProductException {
        ProductSpecification updatedProductSpecification = productSpecificationService.updateProductSpecification(id,
                productSpecificationDetails);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "Product specification updated successfully", updatedProductSpecification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductSpecification(@PathVariable Long id)
            throws ProductException {
        productSpecificationService.deleteProductSpecification(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Product specification deleted successfully", null));
    }
}