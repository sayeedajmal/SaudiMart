package com.saudiMart.Product.graphql.resolver;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Service.ProductsService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductQueryResolver implements GraphQLQueryResolver {

    private final ProductsService productsService;

    @Autowired
    public ProductQueryResolver(ProductsService productsService) {
        this.productsService = productsService;
    }

    public Products productById(String id) {
        try {
            return productsService.getProductById(id);
        } catch (Exception e) {
            // Handle exception appropriately, maybe return null or throw a GraphQL error
            e.printStackTrace();
            return null;
        }
    }

    public Page<Products> allProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return productsService.getAllProducts(pageable);
    }

    public Page<Products> productsByCategoryName(String categoryName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        try {
            return productsService.getProductByCategoryName(categoryName, pageable);
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            return Page.empty(pageable);
        }
    }
}