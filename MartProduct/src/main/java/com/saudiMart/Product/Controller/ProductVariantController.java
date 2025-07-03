package com.saudiMart.Product.Controller;

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

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ProductVariantService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/productvariants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ProductVariant>>> getAllProductVariants(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<ProductVariant> productVariants = productVariantService.searchProductVariants(productId, sku, available,
                pageable);
        ResponseWrapper<Page<ProductVariant>> response = new ResponseWrapper<>(200,
                "Successfully retrieved all product variants", productVariants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> getProductVariantById(@PathVariable String id)
            throws ProductException {
        ProductVariant productVariant = productVariantService.getProductVariantById(id);

        ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(200, "Successfully retrieved product variant",
                productVariant);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductVariant>> createProductVariant(
            @RequestBody ProductVariant productVariant) throws ProductException {
        ProductVariant createdProductVariant = productVariantService.createProductVariant(productVariant);
        ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(200, "Successfully created product variant",
                createdProductVariant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductVariant>> updateProductVariant(@PathVariable String id,
            @RequestBody ProductVariant productVariantDetails) throws ProductException {
        ProductVariant updatedProductVariant = productVariantService.updateProductVariant(id, productVariantDetails);
        if (updatedProductVariant != null) {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(200,
                    "Successfully updated product variant", updatedProductVariant);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<ProductVariant> response = new ResponseWrapper<>(404, "Product variant not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductVariant(@PathVariable String id) throws ProductException {
        productVariantService.deleteProductVariant(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>(200, "Successfully deleted product variant", null);
        return ResponseEntity.ok(response);
    }
}