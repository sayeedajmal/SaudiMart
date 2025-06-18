package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProduct(Products product);

    List<ProductImage> findByVariant(ProductVariant variant);

    List<ProductImage> findByProductAndIsPrimaryTrue(Products product);

    List<ProductImage> findByProductOrderByDisplayOrderAsc(Products product);
}
