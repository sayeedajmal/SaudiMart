package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ContractItemRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ContractItemService {

    @Autowired
    private ContractItemRepository contractItemRepository;

    public List<ContractItem> getAllContractItems() {
        return contractItemRepository.findAll();
    }

    public ContractItem getContractItemById(String id) throws ProductException {
        return contractItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Contract Item not found with id: " + id));
    }

    public List<ContractItem> getContractItemsByContract(Contract contract) {
        return contractItemRepository.findByContract(contract);
    }

    public List<ContractItem> getContractItemsByProduct(Products products) {
        return contractItemRepository.findByProduct(products);
    }

    public List<ContractItem> getContractItemsByVariant(ProductVariant variant) {
        return contractItemRepository.findByVariant(variant);
    }

    public ContractItem createContractItem(ContractItem contractItem) throws ProductException {
        if (contractItem == null) {
            throw new ProductException("Contract Item cannot be null");
        }
        return contractItemRepository.save(contractItem);
    }

    public ContractItem updateContractItem(String id, ContractItem contractItemDetails) throws ProductException {
        if (contractItemDetails == null) {
            throw new ProductException("Contract Item details cannot be null for update");
        }
        Optional<ContractItem> contractItemOptional = contractItemRepository.findById(id);
        if (contractItemOptional.isPresent()) {
            ContractItem contractItem = contractItemOptional.get();
            if (contractItemDetails.getNegotiatedPrice() != null) {
                contractItem.setNegotiatedPrice(contractItemDetails.getNegotiatedPrice());
            }
            if (contractItemDetails.getMinCommitmentQuantity() != null) {
                contractItem.setMinCommitmentQuantity(contractItemDetails.getMinCommitmentQuantity());
            }
            if (contractItemDetails.getMaxQuantity() != null) {
                contractItem.setMaxQuantity(contractItemDetails.getMaxQuantity());
            }
            if (contractItemDetails.getDiscountPercent() != null) {
                contractItem.setDiscountPercent(contractItemDetails.getDiscountPercent());
            }
            if (contractItemDetails.getIsActive() != null) {
                contractItem.setIsActive(contractItemDetails.getIsActive());
            }
            // Assuming product and variant are not changed during update of a contract item
            return contractItemRepository.save(contractItem);
        }
        throw new ProductException("Contract Item not found with id: " + id);
    }

    public void deleteContractItem(String id) throws ProductException {
        if (!contractItemRepository.existsById(id)) {
            throw new ProductException("Contract Item not found with id: " + id);
        }
        contractItemRepository.deleteById(id);
    }
}