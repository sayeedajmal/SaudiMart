package com.saudiMart.Product.Service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ContractItemRepository;
import com.saudiMart.Product.Repository.ContractRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ContractItemService {

    @Autowired
    private ContractItemRepository contractItemRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public Page<ContractItem> getAllContractItems(Pageable pageable) {
        return contractItemRepository.findAll(pageable);
    }

    public ContractItem getContractItemById(String id) throws ProductException {
        return contractItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Contract Item not found with id: " + id));
    }

    public Page<ContractItem> getContractItemsByContract(Contract contract, Pageable pageable) throws ProductException {
        return contractItemRepository.findByContract(contract, pageable);
    }

    public Page<ContractItem> getContractItemsByProduct(Products product, Pageable pageable) throws ProductException {
        return contractItemRepository.findByProduct(product, pageable);
    }

    public Page<ContractItem> getContractItemsByVariant(ProductVariant variant, Pageable pageable)
            throws ProductException {
        return contractItemRepository.findByVariant(variant, pageable);
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

    public Page<ContractItem> searchContractItems(
            String contractId,
            String productId,
            String variantId,
            BigDecimal minNegotiatedPrice,
            BigDecimal maxNegotiatedPrice,
            Integer minCommitmentQuantity,
            Integer maxQuantity,
            BigDecimal minDiscountPercent,
            BigDecimal maxDiscountPercent,
            Boolean isActive,
            Pageable pageable) throws ProductException {

        Specification<ContractItem> spec = Specification.where(null); // Start with a null specification

        if (contractId != null) {
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new ProductException("Contract not found with id: " + contractId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("contract"), contract));
        }
        if (productId != null) {
            Products product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("product"), product));
        }
        if (variantId != null) {
            ProductVariant variant = productVariantRepository.findById(variantId)
                    .orElseThrow(
                            () -> new ProductException("Product Variant not found with id: " + variantId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("variant"), variant));
        }
        if (minNegotiatedPrice != null) {
            spec = spec
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("negotiatedPrice"), minNegotiatedPrice));
        }
        if (maxNegotiatedPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("negotiatedPrice"), maxNegotiatedPrice));
        }
        if (minCommitmentQuantity != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("minCommitmentQuantity"),
                    minCommitmentQuantity));
        }
        if (maxQuantity != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("maxQuantity"), maxQuantity));
        }
        if (isActive != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isActive"), isActive));
        }

        return contractItemRepository.findAll(spec, pageable);
    }
}