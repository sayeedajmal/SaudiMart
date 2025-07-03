package com.saudiMart.Product.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Repository.InventoryRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Repository.WarehouseRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Page<Inventory> getAllInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    public Inventory getInventoryById(String id) throws ProductException {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ProductException("Inventory not found with id: " + id));
    }

    public Page<Inventory> getInventoryByProductId(String productId, Pageable pageable) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return inventoryRepository.findByProduct(product, pageable);
    }

    public Page<Inventory> getInventoryByVariantId(String variantId, Pageable pageable) throws ProductException {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
        return inventoryRepository.findByVariant(variant, pageable);
    }

    public Page<Inventory> getInventoryByWarehouseId(String warehouseId, Pageable pageable) throws ProductException {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ProductException("Warehouse not found with id: " + warehouseId));
        return inventoryRepository.findByWarehouse(warehouse, pageable);
    }

    public Inventory createInventory(Inventory inventory) throws ProductException {
        if (inventory == null) {
            throw new ProductException("Inventory cannot be null");
        }
        // Add any business logic validation before saving
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(String id, Inventory inventoryDetails) throws ProductException {
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

            return inventoryRepository.save(existingInventory);
        }
        throw new ProductException("Inventory not found with id: " + id);
    }

    public void deleteInventory(String id) throws ProductException {
        if (!inventoryRepository.existsById(id)) {
            throw new ProductException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }

    public Page<Inventory> searchInventory(String productId, String variantId, String warehouseId,
            Integer minQuantity, Integer maxQuantity, Integer minReservedQuantity,
            Integer maxReservedQuantity, Pageable pageable) throws ProductException {

        Products product = null;
        if (productId != null) {
            product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        }

        ProductVariant variant = null;
        if (variantId != null) {
            variant = productVariantRepository.findById(variantId)
                    .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
        }

        Warehouse warehouse = null;
        if (warehouseId != null) {
            warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new ProductException("Warehouse not found with id: " + warehouseId));
        }

        if (product != null && variant != null && warehouse != null) {
            return inventoryRepository.findByProductAndVariantAndWarehouse(product, variant, warehouse, pageable);
        } else if (product != null && variant != null) {
            return inventoryRepository.findByProductAndVariant(product, variant, pageable);
        }
        return inventoryRepository.findAll(pageable);
    }

    public Page<Inventory> getInventoryByWarehouse(Warehouse warehouse, Pageable pageable) {
        return inventoryRepository.findByWarehouse(warehouse, pageable);
    }
}