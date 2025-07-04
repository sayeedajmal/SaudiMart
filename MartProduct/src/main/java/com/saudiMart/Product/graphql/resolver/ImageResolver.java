package com.saudiMart.Product.graphql.resolver;

import com.saudiMart.Product.Model.ProductImage;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

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