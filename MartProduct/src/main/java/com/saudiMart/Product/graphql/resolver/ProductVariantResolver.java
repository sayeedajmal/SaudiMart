package com.saudiMart.Product.graphql.resolver;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.ProductImage;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.util.List;

@Component
public class ProductVariantResolver implements GraphQLResolver<ProductVariant> {

    public List<ProductImage> getImages(ProductVariant productVariant) {
        return productVariant.getImages();
    }

    public String getSku(ProductVariant productVariant) {
        return productVariant.getSku();
    }

    public String getVariantName(ProductVariant productVariant) {
        return productVariant.getVariantName();
    }

    public BigDecimal getBasePrice(ProductVariant productVariant) {
        return productVariant.getBasePrice();
    }
}