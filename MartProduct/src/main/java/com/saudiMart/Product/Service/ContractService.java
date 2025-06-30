package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Contract.ContractStatus;
import com.saudiMart.Product.Repository.ContractRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

 @Autowired
 private UserService userService; // Assuming you have a UserService to fetch Users

    public Page<Contract> getAllContracts(Pageable pageable) {
        return contractRepository.findAll(pageable);
    }

    public Contract getContractById(String id) throws ProductException {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ProductException("Contract not found with id: " + id));
    }

    public Contract createContract(Contract contract) throws ProductException {
        if (contract == null) {
            throw new ProductException("Contract cannot be null");
        }
        // Add any business logic validation before saving
        return contractRepository.save(contract);
    }

    public Contract updateContract(String id, Contract contractDetails) throws ProductException {
        if (contractDetails == null) {
            throw new ProductException("Contract details cannot be null for update");
        }
        Contract existingContract = getContractById(id);

        if (contractDetails.getBuyer() != null) {
            existingContract.setBuyer(contractDetails.getBuyer());
        }
        if (contractDetails.getSeller() != null) {
            existingContract.setSeller(contractDetails.getSeller());
        }
        if (contractDetails.getContractNumber() != null) {
            existingContract.setContractNumber(contractDetails.getContractNumber());
        }
        if (contractDetails.getStartDate() != null) {
            existingContract.setStartDate(contractDetails.getStartDate());
        }
        if (contractDetails.getEndDate() != null) {
            existingContract.setEndDate(contractDetails.getEndDate());
        }
        if (contractDetails.getPaymentTerms() != null) {
            existingContract.setPaymentTerms(contractDetails.getPaymentTerms());
        }
        if (contractDetails.getCreditLimit() != null) {
            existingContract.setCreditLimit(contractDetails.getCreditLimit());
        }
        if (contractDetails.getStatus() != null) {
            existingContract.setStatus(contractDetails.getStatus());
        }
        if (contractDetails.getTermsAndConditions() != null) {
            existingContract.setTermsAndConditions(contractDetails.getTermsAndConditions());
        }

        return contractRepository.save(existingContract);
    }

    public void deleteContract(String id) throws ProductException {
        if (!contractRepository.existsById(id)) {
            throw new ProductException("Contract not found with id: " + id);
        }
        contractRepository.deleteById(id);
    }

 public Page<Contract> getContractsByBuyer(Users user, Pageable pageable) {
 return contractRepository.findByBuyer(user, pageable);
    }

 public Page<Contract> getContractsBySeller(Users user, Pageable pageable) {
 return contractRepository.findBySeller(user, pageable);
    }

 public Page<Contract> getContractsByStatus(ContractStatus status, Pageable pageable) {
 return contractRepository.findByStatus(status, pageable);
    }

 public Page<Contract> searchContracts(String buyerId, String sellerId, String contractNumber, ContractStatus status,
 LocalDate startDate, LocalDate endDate, BigDecimal minCreditLimit, BigDecimal maxCreditLimit, Pageable pageable)
 throws ProductException {
 Specification<Contract> spec = Specification.where(null);

 if (buyerId != null && !buyerId.isEmpty()) {
 Optional<Users> buyer = userService.getUserByIdOptional(buyerId);
 if (buyer.isPresent()) {
 spec = spec.and((root, query, builder) -> builder.equal(root.get("buyer"), buyer.get()));
            } else {
 // If buyer not found, return empty page
 return Page.empty(pageable);
            }
        }

 if (sellerId != null && !sellerId.isEmpty()) {
 Optional<Users> seller = userService.getUserByIdOptional(sellerId);
 if (seller.isPresent()) {
 spec = spec.and((root, query, builder) -> builder.equal(root.get("seller"), seller.get()));
            } else {
 // If seller not found, return empty page
 return Page.empty(pageable);
            }
        }

 if (contractNumber != null && !contractNumber.isEmpty()) {
 spec = spec.and((root, query, builder) -> builder.equal(root.get("contractNumber"), contractNumber));
        }

 if (status != null) {
 spec = spec.and((root, query, builder) -> builder.equal(root.get("status"), status));
        }

 if (startDate != null) {
 spec = spec.and((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("startDate"), startDate));
        }

 if (endDate != null) {
 spec = spec.and((root, query, builder) -> builder.lessThanOrEqualTo(root.get("endDate"), endDate));
        }

 if (minCreditLimit != null) {
 spec = spec.and((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("creditLimit"), minCreditLimit));
        }

 if (maxCreditLimit != null) {
 spec = spec.and((root, query, builder) -> builder.lessThanOrEqualTo(root.get("creditLimit"), maxCreditLimit));
        }

 // If no specific criteria are provided, return all paginated contracts
 if (spec.equals(Specification.where(null))) {
 return contractRepository.findAll(pageable);
        }
 return contractRepository.findAll(spec, pageable);
    }
}