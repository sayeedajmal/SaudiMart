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
@RequestMapping("/productimages")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> getAllProductImages() {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        return ResponseEntity.ok(new ResponseWrapper<>(productImages, "Successfully retrieved all product images.", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> getProductImageById(@PathVariable Long id) {
        Optional<ProductImage> productImage = productImageService.getProductImageById(id);
        return productImage.map(image -> ResponseEntity.ok(new ResponseWrapper<>(image, "Successfully retrieved product image.", true)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product image not found.", false)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductImage>> createProductImage(@RequestBody ProductImage productImage) {
        ProductImage createdProductImage = productImageService.createProductImage(productImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(createdProductImage, "Successfully created product image.", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> updateProductImage(@PathVariable Long id, @RequestBody ProductImage productImageDetails) {
        ProductImage updatedProductImage = productImageService.updateProductImage(id, productImageDetails);
        if (updatedProductImage != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(updatedProductImage, "Successfully updated product image.", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product image not found for update.", false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductImage(@PathVariable Long id) {
        try {
            productImageService.deleteProductImage(id);
            return ResponseEntity.ok(new ResponseWrapper<>(null, "Successfully deleted product image.", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product image not found for deletion.", false));
        }
    }
}