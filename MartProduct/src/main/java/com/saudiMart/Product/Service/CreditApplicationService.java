package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplicationStatus; // Assuming you create this enum
import com.saudiMart.Product.Repository.CreditApplicationRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class CreditApplicationService {

    @Autowired
    private CreditApplicationRepository creditApplicationRepository;

    public List<CreditApplication> getAllCreditApplications() {
        return creditApplicationRepository.findAll();
    }

    public CreditApplication getCreditApplicationById(Long id) throws ProductException {
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

    public CreditApplication updateCreditApplication(Long id, CreditApplication creditApplicationDetails)
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

            // You might want to update updated_at here manually or rely on @PreUpdate
            // existingCreditApplication.setUpdatedAt(LocalDateTime.now());

            return creditApplicationRepository.save(existingCreditApplication);
        }
        throw new ProductException("Credit Application not found with id: " + id);
    }

    public void deleteCreditApplication(Long id) throws ProductException {
        if (!creditApplicationRepository.existsById(id)) {
            throw new ProductException("Credit Application not found with id: " + id);
        }
        creditApplicationRepository.deleteById(id);
    }

    public List<CreditApplication> getCreditApplicationsByBuyerId(Long buyerId) {
        return creditApplicationRepository.findByBuyerId(buyerId);
    }

    public List<CreditApplication> getCreditApplicationsBySellerId(Long sellerId) {
        return creditApplicationRepository.findBySellerId(sellerId);
    }

    public List<CreditApplication> getCreditApplicationsByStatus(CreditApplicationStatus status) {
        return creditApplicationRepository.findByStatus(status);
    }

    public List<CreditApplication> getCreditApplicationsByReviewerId(Long reviewerId) {
        return creditApplicationRepository.findByReviewerId(reviewerId);
    }
}