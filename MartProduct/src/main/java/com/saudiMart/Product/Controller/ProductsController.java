package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductsService;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Products>>> getAllProducts() {
        try {
            List<Products> products = productsService.getAllProducts();
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Products fetched successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching products: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> getProductById(@PathVariable Long id) {
        try {
            Optional<Products> product = productsService.getProductById(id);
            if (product.isPresent()) {
                ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product fetched successfully", product.get());
                return ResponseEntity.ok(response);
            } else {
                ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "Product not found with id: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

 @GetMapping("/seller/{sellerId}")
 public ResponseEntity<ResponseWrapper<List<Products>>> getProductsBySellerId(@PathVariable Long sellerId) {
 try {
 List<Products> products = productsService.getProductsBySellerId(sellerId);
 ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Products by seller fetched successfully", products);
 return ResponseEntity.ok(response);
 } catch (Exception e) {
 ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching products by seller: " + e.getMessage(), null);
 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
 }
    }
    @PostMapping
    public ResponseEntity<ResponseWrapper<Products>> createProduct(@RequestBody Products product) throws ProductException {
        try {
            Products createdProduct = productsService.createProduct(product);
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.CREATED.value(), "Product created successfully", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error creating product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> updateProduct(@PathVariable Long id, @RequestBody Products productDetails) throws ProductException {
        try {
            Products updatedProduct = productsService.updateProduct(id, productDetails);
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Product updated successfully", updatedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable Long id) throws ProductException {
        try {
            productsService.deleteProduct(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.NO_CONTENT.value(), "Product deleted successfully", null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}