package com.saudiMart.Product.graphql.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Users;

@Component
public class ProductResolver implements GraphQLResolver<Products> {

    public Category category(Products product) {
        return product.getCategory();
    }

    public List<ProductVariant> variants(Products product) {
        return product.getVariants();
    }

    public Users seller(Products product) {
        return product.getSeller();
    }

    public List<ProductSpecification> specifications(Products product) {
        return product.getSpecifications();
    }
}