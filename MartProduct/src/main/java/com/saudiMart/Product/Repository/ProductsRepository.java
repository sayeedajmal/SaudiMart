package com.saudiMart.Product.Repository;

import com.saudiMart.Product.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    // You can add custom query methods here if needed
    List<Products> findBySellerId(Long sellerId);
}