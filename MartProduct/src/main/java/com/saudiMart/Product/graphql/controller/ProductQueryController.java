package com.saudiMart.Product.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Service.ProductsService;

@Controller
public class ProductQueryController {

    private final ProductsService productsService;

    @Autowired
    public ProductQueryController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @QueryMapping
    public Products productById(@Argument String id) {
        System.out.println("Before getting product by ID");
        try {
            return productsService.getProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            System.out.println("After getting product by ID");
        }
    }

    @QueryMapping
    public Page<Products> allProducts(@Argument Integer page, @Argument Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return productsService.getAllProducts(pageable);
    }

    @QueryMapping
    public Page<Products> productsByCategoryName(@Argument String categoryName, @Argument Integer page,
            @Argument Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        System.out.println("Before getting products by category name");
        try {
            Page<Products> products = productsService.getProductByCategoryName(categoryName, pageable);
            System.out.println("After getting products by category name");
            return products;
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            return Page.empty(pageable);
        }
    }
}