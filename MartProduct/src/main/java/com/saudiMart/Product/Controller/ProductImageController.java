package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductImageService;
import com.saudiMart.Product.Model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

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
        ProductImage createdProductImage = productImageService.saveProductImage(productImage);
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

    @GetMapping("/by-image-id/{imageId}")
    public ResponseEntity<ResponseWrapper<ProductImage>> findProductImageByImageId(@PathVariable Long imageId) {
        ProductImage productImage = productImageService.findProductImageByImageId(imageId);
        ResponseWrapper<ProductImage> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product image fetched by image ID successfully", productImage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-product")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByProduct(@RequestBody Products product) {
        List<ProductImage> productImages = productImageService.findProductImagesByProduct(product);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by product successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-variant-id/{variantId}")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByVariantId(@PathVariable Long variantId) {
        List<ProductImage> productImages = productImageService.findProductImagesByVariantId(variantId);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by variant ID successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-image-url")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByImageUrl(@RequestBody String imageUrl) {
        List<ProductImage> productImages = productImageService.findProductImagesByImageUrl(imageUrl);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by image URL successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-alt-text")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByAltText(@RequestBody String altText) {
        List<ProductImage> productImages = productImageService.findProductImagesByAltText(altText);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by alt text successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-display-order/{displayOrder}")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByDisplayOrder(@PathVariable Integer displayOrder) {
        List<ProductImage> productImages = productImageService.findProductImagesByDisplayOrder(displayOrder);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by display order successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/primary/{isPrimary}")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByIsPrimary(@PathVariable Boolean isPrimary) {
        List<ProductImage> productImages = productImageService.findProductImagesByIsPrimary(isPrimary);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by primary status successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/created-between")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findProductImagesByCreatedAtBetween(@RequestBody Timestamp start, @RequestBody Timestamp end) {
        List<ProductImage> productImages = productImageService.findProductImagesByCreatedAtBetween(start, end);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product images fetched by creation date range successfully", productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/primary")
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> findPrimaryProductImageByProductId(@PathVariable Long productId) {
        List<ProductImage> productImages = productImageService.findPrimaryProductImageByProductId(productId);
        ResponseWrapper<List<ProductImage>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Primary product images fetched by product ID successfully", productImages);
        return ResponseEntity.ok(response);
    }

}