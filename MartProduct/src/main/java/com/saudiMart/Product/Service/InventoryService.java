package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Repository.InventoryRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) throws ProductException {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ProductException("Inventory not found with id: " + id));
    }

    public List<Inventory> getInventoryByProduct(Products product) {
        return inventoryRepository.findByProduct(product);
    }

    public List<Inventory> getInventoryByVariant(ProductVariant variant) {
        return inventoryRepository.findByVariant(variant);
    }

    public List<Inventory> getInventoryByWarehouse(Warehouse warehouse) {
        return inventoryRepository.findByWarehouse(warehouse);
    }

    public Inventory createInventory(Inventory inventory) throws ProductException {
        if (inventory == null) {
            throw new ProductException("Inventory cannot be null");
        }
        // Add any business logic validation before saving
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails) throws ProductException {
        if (inventoryDetails == null) {
            throw new ProductException("Inventory details cannot be null for update");
        }
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (inventoryOptional.isPresent()) {
            Inventory existingInventory = inventoryOptional.get();

            if (inventoryDetails.getQuantity() != null) {
                existingInventory.setQuantity(inventoryDetails.getQuantity());
            }
            if (inventoryDetails.getReservedQuantity() != null) {
                existingInventory.setReservedQuantity(inventoryDetails.getReservedQuantity());
            }

            // Assuming product, variant, and warehouse are not changed during update of inventory
            // You might need to add methods for updating these relationships if required

            return inventoryRepository.save(existingInventory);
        }
        throw new ProductException("Inventory not found with id: " + id);
    }

    public void deleteInventory(Long id) throws ProductException {
        if (!inventoryRepository.existsById(id)) {
            throw new ProductException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }
}