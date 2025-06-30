package com.saudiMart.Product.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Page<Category> findByIsActive(Boolean isActive, Pageable pageable);

    Page<Category> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Category> findByName(String name);
}
