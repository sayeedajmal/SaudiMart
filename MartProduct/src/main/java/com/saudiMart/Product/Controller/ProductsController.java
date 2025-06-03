package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Products>>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return ResponseEntity.ok(new ResponseWrapper<>(products, "Successfully retrieved all products"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> getProductById(@PathVariable Long id) {
        Optional<Products> product = productsService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(product.get(), "Successfully retrieved product"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product not found"));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Products>> createProduct(@RequestBody Products product) {
        Products createdProduct = productsService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(createdProduct, "Successfully created product"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> updateProduct(@PathVariable Long id, @RequestBody Products productDetails) {
        Products updatedProduct = productsService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(updatedProduct, "Successfully updated product"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(null, "Product not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseWrapper<>(null, "Successfully deleted product"));
    }
}