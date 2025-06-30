package com.saudiMart.Product.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductVariant;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    Page<ProductImage> findByVariant(ProductVariant variant);

}
