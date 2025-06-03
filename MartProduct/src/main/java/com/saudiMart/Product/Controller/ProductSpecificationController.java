package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.Products;
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

    @GetMapping("/by-spec-id/{specId}")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationBySpecId(@PathVariable Long specId) {
        Optional<ProductSpecification> productSpecification = productSpecificationService.findProductSpecificationBySpecId(specId);
        if (productSpecification.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specification retrieved successfully", productSpecification.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product specification not found", null));
        }
    }

    @GetMapping("/by-product")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByProduct(@RequestBody Products product) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsByProduct(product);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by product successfully", productSpecifications));
    }

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByProductId(@PathVariable Long productId) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsByProductId(productId);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by product ID successfully", productSpecifications));
    }

    @GetMapping("/by-spec-name")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsBySpecName(@RequestBody String specName) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsBySpecName(specName);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by spec name successfully", productSpecifications));
    }

    @GetMapping("/by-spec-value")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsBySpecValue(@RequestBody String specValue) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsBySpecValue(specValue);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by spec value successfully", productSpecifications));
    }

    @GetMapping("/by-unit")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByUnit(@RequestBody String unit) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsByUnit(unit);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by unit successfully", productSpecifications));
    }

    @GetMapping("/by-display-order/{displayOrder}")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByDisplayOrder(@PathVariable Integer displayOrder) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsByDisplayOrder(displayOrder);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by display order successfully", productSpecifications));
    }

    @GetMapping("/by-product-id/{productId}/spec-name")
    public ResponseEntity<ResponseWrapper<ProductSpecification>> getProductSpecificationByProductIdAndSpecName(@PathVariable Long productId, @RequestBody String specName) {
        Optional<ProductSpecification> productSpecification = productSpecificationService.findProductSpecificationByProductIdAndSpecName(productId, specName);
        if (productSpecification.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specification retrieved successfully", productSpecification.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product specification not found", null));
        }
    }

    @GetMapping("/by-product-id/{productId}/ordered")
    public ResponseEntity<ResponseWrapper<List<ProductSpecification>>> getProductSpecificationsByProductIdOrdered(@PathVariable Long productId) {
        List<ProductSpecification> productSpecifications = productSpecificationService.findProductSpecificationsByProductIdOrdered(productId);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Product specifications retrieved by product ID and ordered successfully", productSpecifications));
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