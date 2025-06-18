package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query("SELECT p FROM Products p WHERE p.seller.id = :sellerId")
    List<Products> findBySellerId(@Param("sellerId") Long sellerId);

    List<Products> findByCategory(Category category);

    List<Products> findByAvailableTrue();

    List<Products> findByNameContainingIgnoreCase(String keyword);
}
