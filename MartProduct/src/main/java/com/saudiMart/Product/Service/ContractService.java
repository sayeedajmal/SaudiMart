package com.saudiMart.Product.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Contract.ContractStatus;
import com.saudiMart.Product.Repository.ContractRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long id) throws ProductException {
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

    public Contract updateContract(Long id, Contract contractDetails) throws ProductException {
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

    public void deleteContract(Long id) throws ProductException {
        if (!contractRepository.existsById(id)) {
            throw new ProductException("Contract not found with id: " + id);
        }
        contractRepository.deleteById(id);
    }

    public List<Contract> getContractsByBuyer(Users user) {
        return contractRepository.findByBuyer(user);
    }

    public List<Contract> getContractsBySeller(Users user) {
        return contractRepository.findBySeller(user);
    }

    public List<Contract> getContractsByStatus(ContractStatus status) {
        return contractRepository.findByStatus(status);
    }
}