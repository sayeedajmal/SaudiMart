package com.saudiMart.Product.graphql.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Service.ProductsService;

@Component
public class ProductQueryResolver implements GraphQLQueryResolver {

    private final ProductsService productsService;

    @Autowired
    public ProductQueryResolver(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PreAuthorize("isAuthenticated()")
    public Products productById(String id) {
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

    public Page<Products> allProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return productsService.getAllProducts(pageable);
    }

    public Page<Products> productsByCategoryName(String categoryName, Integer page, Integer size) {
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