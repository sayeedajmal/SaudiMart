package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productimages")
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> getAllProductImages() {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
 public ResponseEntity<ResponseWrapper<List<ProductImage>>> getProductImagesByProductId(@PathVariable Long productId) {
 List<ProductImage> productImages = productImageService.getProductImagesByProductId(productId);
 ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by product ID successfully", productImages);
 return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> getProductImageById(@PathVariable Long id) {
        Optional<ProductImage> productImage = productImageService.getProductImageById(id);
        if (productImage.isPresent()) {
            ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product image fetched successfully", productImage.get());
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product image not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductImage>> createProductImage(@RequestBody ProductImage productImage) {
        ProductImage createdProductImage = productImageService.createProductImage(productImage);
        ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.CREATED.value(), "Product image created successfully", createdProductImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> updateProductImage(@PathVariable Long id, @RequestBody ProductImage productImageDetails) {
        ProductImage updatedProductImage = productImageService.updateProductImage(id, productImageDetails);
        if (updatedProductImage != null) {
            ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product image updated successfully", updatedProductImage);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product image not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductImage(@PathVariable Long id) {
        productImageService.deleteProductImage(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.NO_CONTENT.value(), "Product image deleted successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}