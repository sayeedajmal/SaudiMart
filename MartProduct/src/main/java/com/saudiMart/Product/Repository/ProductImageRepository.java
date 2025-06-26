package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    List<ProductImage> findByProduct(Products product);

    List<ProductImage> findByVariant(ProductVariant variant);

    List<ProductImage> findByProductAndIsPrimaryTrue(Products product);

    List<ProductImage> findByProductOrderByDisplayOrderAsc(Products product);

    void deleteByProduct(Products product);
}
