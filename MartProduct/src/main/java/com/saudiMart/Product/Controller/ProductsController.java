package com.saudiMart.Product.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Products>>> getAllProducts() {
        try {
            List<Products> products = productsService.getAllProducts();
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> getProductById(@PathVariable Long id) {
        try {
            Optional<Products> product = productsService.findProductByProductId(id);
            if (product.isPresent()) {
                ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Product fetched successfully", product.get());
                return ResponseEntity.ok(response);
            } else {
                ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
                        "Product not found with id: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ResponseWrapper<List<Products>>> getProductsBySellerId(@PathVariable Long sellerId) {
        try {
            List<Products> products = productsService.getProductsBySellerId(sellerId);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products by seller fetched successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by seller: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Products>> createProduct(@RequestBody Products product)
            throws ProductException {
        try {
            Products createdProduct = productsService.createProduct(product);
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.CREATED.value(),
                    "Product created successfully", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error creating product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Products>> updateProduct(@PathVariable Long id,
            @RequestBody Products productDetails) throws ProductException {
        try {
            Products updatedProduct = productsService.updateProduct(id, productDetails);
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Product updated successfully", updatedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<Products> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable Long id) throws ProductException {
        try {
            productsService.deleteProduct(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.NO_CONTENT.value(),
                    "Product deleted successfully", null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error deleting product: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<ResponseWrapper<Optional<Products>>> findProductByProductId(@PathVariable Long productId) {
        try {
            Optional<Products> product = productsService.findProductByProductId(productId);
            ResponseWrapper<Optional<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Product fetched by product ID successfully", product);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<Optional<Products>> response = new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching product by product ID: " + e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByCategoryId(@PathVariable Long categoryId) {
        try {
            List<Products> products = productsService.findProductsByCategoryId(categoryId);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by category ID successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by category ID: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByName(@RequestBody String name) {
        try {
            List<Products> products = productsService.findProductsByName(name);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by name successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by name: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<ResponseWrapper<List<Products>>> searchProductsByName(@RequestBody String name) {
        try {
            List<Products> products = productsService.searchProductsByName(name);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products searched by name successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error searching products by name: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-sku/{sku}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsBySku(@PathVariable String sku) {
        try {
            List<Products> products = productsService.findProductsBySku(sku);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by SKU successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by SKU: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/available/{available}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByAvailable(@PathVariable Boolean available) {
        try {
            List<Products> products = productsService.findProductsByAvailable(available);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by availability successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by availability: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-base-price/{basePrice}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByBasePrice(@PathVariable BigDecimal basePrice) {
        try {
            List<Products> products = productsService.findProductsByBasePrice(basePrice);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by base price successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by base price: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/bulk-only/{isBulkOnly}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByIsBulkOnly(@PathVariable Boolean isBulkOnly) {
        try {
            List<Products> products = productsService.findProductsByIsBulkOnly(isBulkOnly);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by bulk only status successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by bulk only status: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-min-order-quantity/{minimumOrderQuantity}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByMinimumOrderQuantity(
            @PathVariable Integer minimumOrderQuantity) {
        try {
            List<Products> products = productsService.findProductsByMinimumOrderQuantity(minimumOrderQuantity);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by minimum order quantity successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by minimum order quantity: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-weight/{weight}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByWeight(@PathVariable BigDecimal weight) {
        try {
            List<Products> products = productsService.findProductsByWeight(weight);
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Products fetched by weight successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching products by weight: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-weight-unit/{weightUnit}")
    public ResponseEntity<ResponseWrapper<List<Products>>> findProductsByWeightUnit(@PathVariable String weightUnit) {
        List<Products> products = productsService.findProductsByWeightUnit(weightUnit);
        ResponseWrapper<List<Products>> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                "Products fetched by weight unit successfully", products);
        return ResponseEntity.ok(response);
    }
}