package com.saudiMart.Product.graphql.resolver;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.saudiMart.Product.Model.ProductImage;

@Component
public class ImageResolver implements GraphQLResolver<ProductImage> {

    public String getImageUrl(ProductImage productImage) {
        return productImage.getImageUrl();
    }

    public String getAltText(ProductImage productImage) {
        return productImage.getAltText();
    }

    public Boolean getIsPrimary(ProductImage productImage) {
        return productImage.getIsPrimary();
    }
}