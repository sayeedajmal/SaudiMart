package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductVariant;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    Page<ProductImage> findByVariant(ProductVariant variant, Pageable pageable);

    List<ProductImage> findByVariant(ProductVariant variant);

}
