package com.saudiMart.Product.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ResponseWrapper<Page<Products>>> getAllProducts(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Products> products = productsService.getAllProducts(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all products", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> getProductById(@PathVariable String id) throws ProductException {
        Products product = productsService.getProductById(id);

        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved product", product));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Products>> createProduct(@RequestBody Products product)
            throws ProductException {
        System.out.println("PROUDCT CATEOGORY::: " + product.getCategory().toString());
        Products createdProduct = productsService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(200, "Successfully created product", createdProduct));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ResponseWrapper<Page<Products>>> getProductsBySellerId(@PathVariable String sellerId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Products> products = productsService.getProductsBySellerId(sellerId, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved products for seller", products));
    }

    @GetMapping("/seller/{sellerId}/available")
    public ResponseEntity<ResponseWrapper<Page<Products>>> getAvailableProductsBySellerId(
            @PathVariable String sellerId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Products> products = productsService.getAvailableProductsBySellerId(sellerId, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved available products for seller", products));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<Products>>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String sellerId,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minPrice,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Products> products = productsService.searchProducts(keywords, categoryId, sellerId, available, minPrice,
                maxPrice, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved products based on search criteria", products));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ResponseWrapper<Page<Products>>> getProductsByCategoryName(
            @PathVariable String categoryName, @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Products> products = productsService.getProductByCategoryName(categoryName, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved products by category", products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> updateProduct(@PathVariable String id,
            @RequestBody Products productDetails) throws ProductException {
        Products updatedProduct = productsService.updateProduct(id, productDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(200, "Product not found", updatedProduct));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable String id) throws ProductException {
        productsService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted product", null));
    }
}
