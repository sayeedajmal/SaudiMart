package com.saudiMart.Product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductImageService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/productimages")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductImage>>> getAllProductImages() throws ProductException {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved all product images.", productImages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> getProductImageById(@PathVariable String id)
            throws ProductException {
        ProductImage productImage = productImageService.getProductImageById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved product image.", productImage));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductImage>> createProductImage(@RequestBody ProductImage productImage)
            throws ProductException {
        ProductImage createdProductImage = productImageService.createProductImage(productImage);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully created product image.", createdProductImage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductImage>> updateProductImage(@PathVariable String id,
            @RequestBody ProductImage productImageDetails) throws ProductException {
        ProductImage updatedProductImage = productImageService.updateProductImage(id, productImageDetails);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully updated product image.", updatedProductImage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductImage(@PathVariable String id) throws ProductException {
        productImageService.deleteProductImage(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted product image.", null));
    }
}