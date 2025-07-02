package com.saudiMart.Product.Repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query("SELECT p FROM Products p WHERE p.seller.id = :sellerId")
    Page<Products> findBySellerId(@Param("sellerId") String sellerId, Pageable pageable);

    Page<Products> findByCategory(Category category, Pageable pageable);

    Page<Products> findByAvailableTrue(Pageable pageable);

    Page<Products> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Products> findBySku(String sku, Pageable pageable);

    Page<Products> findByAvailable(Boolean available, Pageable pageable);

    Page<Products> findByBasePriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT p FROM Products p WHERE " +
           "(:keyword is null or lower(p.name) like lower(concat('%', :keyword, '%')) or lower(p.description) like lower(concat('%', :keyword, '%'))) and " +
           "(:category is null or p.category = :category) and " +
           "(:sellerId is null or p.seller.id = :sellerId) and " +
           "(:available is null or p.available = :available) and " +
           "(:minPrice is null or p.basePrice >= :minPrice) and " +
           "(:maxPrice is null or p.basePrice <= :maxPrice)")
    Page<Products> searchProducts(@Param("keyword") String keyword, @Param("category") Category category,
                                  @Param("sellerId") String sellerId, @Param("available") Boolean available,
                                  @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);

}
