package com.saudiMart.Product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByIsActive(Boolean isActive);

    List<Category> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive);

    List<Category> findByNameContainingIgnoreCase(String name);

    Optional<Category> findByName(String name);
}
