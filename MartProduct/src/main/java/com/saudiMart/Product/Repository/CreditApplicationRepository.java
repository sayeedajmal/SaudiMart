package com.saudiMart.Product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplication.CreditApplicationStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, String> {

    Page<CreditApplication> findByBuyer(Users buyer, Pageable pageable);

    Page<CreditApplication> findBySeller(Users seller, Pageable pageable);

    Page<CreditApplication> findByStatus(CreditApplicationStatus status, Pageable pageable);

    Page<CreditApplication> findByReviewer(Users reviewer, Pageable pageable);

    Page<CreditApplication> findByBuyerAndSeller(Users buyer, Users seller, Pageable pageable);

    Optional<CreditApplication> findByIdAndSeller(String id, Users seller);

    Optional<CreditApplication> findByIdAndBuyer(String id, Users buyer);
}