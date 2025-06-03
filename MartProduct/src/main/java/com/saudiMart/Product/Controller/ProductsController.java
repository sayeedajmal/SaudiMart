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

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductsService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Products>>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all products", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> getProductById(@PathVariable Long id) throws ProductException {
        Products product = productsService.getProductById(id);

        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved product", product));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Products>> createProduct(@RequestBody Products product)
            throws ProductException {
        Products createdProduct = productsService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(200, "Successfully created product", createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> updateProduct(@PathVariable Long id,
            @RequestBody Products productDetails) throws ProductException {
        Products updatedProduct = productsService.updateProduct(id, productDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(200, "Product not found", updatedProduct));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted product", null));
    }
}
