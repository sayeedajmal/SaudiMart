package com.saudiMart.Product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplicationStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

    List<CreditApplication> findByBuyer(Users buyer);

    List<CreditApplication> findBySeller(Users seller);

    List<CreditApplication> findByStatus(CreditApplicationStatus status);

    List<CreditApplication> findByReviewer(Users reviewer);

    List<CreditApplication> findByBuyerAndSeller(Users buyer, Users seller);

    Optional<CreditApplication> findByIdAndSeller(Long id, Users seller);

    Optional<CreditApplication> findByIdAndBuyer(Long id, Users buyer);

}