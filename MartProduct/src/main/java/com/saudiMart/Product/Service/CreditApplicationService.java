package com.saudiMart.Product.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplication.CreditApplicationStatus;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.CreditApplicationRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class CreditApplicationService {

    @Autowired
    private CreditApplicationRepository creditApplicationRepository;

    public Page<CreditApplication> getAllCreditApplications(Pageable pageable) {
        return creditApplicationRepository.findAll(pageable);
    }

    public CreditApplication getCreditApplicationById(String id) throws ProductException {
        return creditApplicationRepository.findById(id)
                .orElseThrow(() -> new ProductException("Credit Application not found with id: " + id));
    }

    public CreditApplication createCreditApplication(CreditApplication creditApplication) throws ProductException {
        if (creditApplication == null) {
            throw new ProductException("Credit Application cannot be null");
        }
        // Add any validation or business logic before saving
        return creditApplicationRepository.save(creditApplication);
    }

    public CreditApplication updateCreditApplication(String id, CreditApplication creditApplicationDetails)
            throws ProductException {
        if (creditApplicationDetails == null) {
            throw new ProductException("Credit Application details cannot be null for update");
        }
        Optional<CreditApplication> creditApplicationOptional = creditApplicationRepository.findById(id);
        if (creditApplicationOptional.isPresent()) {
            CreditApplication existingCreditApplication = creditApplicationOptional.get();

            if (creditApplicationDetails.getRequestedLimit() != null)
                existingCreditApplication.setRequestedLimit(creditApplicationDetails.getRequestedLimit());
            if (creditApplicationDetails.getApprovedLimit() != null)
                existingCreditApplication.setApprovedLimit(creditApplicationDetails.getApprovedLimit());
            if (creditApplicationDetails.getStatus() != null)
                existingCreditApplication.setStatus(creditApplicationDetails.getStatus());
            if (creditApplicationDetails.getReviewDate() != null)
                existingCreditApplication.setReviewDate(creditApplicationDetails.getReviewDate());
            if (creditApplicationDetails.getExpiryDate() != null)
                existingCreditApplication.setExpiryDate(creditApplicationDetails.getExpiryDate());
            if (creditApplicationDetails.getReviewer() != null)
                existingCreditApplication.setReviewer(creditApplicationDetails.getReviewer());
            if (creditApplicationDetails.getNotes() != null)
                existingCreditApplication.setNotes(creditApplicationDetails.getNotes());

            existingCreditApplication.setUpdatedAt(LocalDateTime.now());

            return creditApplicationRepository.save(existingCreditApplication);
        }
        throw new ProductException("Credit Application not found with id: " + id);
    }

    public void deleteCreditApplication(String id) throws ProductException {
        if (!creditApplicationRepository.existsById(id)) {
            throw new ProductException("Credit Application not found with id: " + id);
        }
        creditApplicationRepository.deleteById(id);
    }

    public Page<CreditApplication> getCreditApplicationsByBuyer(Users user, Pageable pageable)
            throws ProductException {
        return creditApplicationRepository.findByBuyer(user, pageable);
    }

    public Page<CreditApplication> getCreditApplicationsBySeller(Users user, Pageable pageable)
            throws ProductException {
        return creditApplicationRepository.findBySeller(user, pageable);
    }

    public Page<CreditApplication> getCreditApplicationsByStatus(CreditApplicationStatus status, Pageable pageable) {
        return creditApplicationRepository.findByStatus(status, pageable);
    }

    public Page<CreditApplication> getCreditApplicationsByReviewer(Users user, Pageable pageable)
            throws ProductException {
        return creditApplicationRepository.findByReviewer(user, pageable);
    }

}