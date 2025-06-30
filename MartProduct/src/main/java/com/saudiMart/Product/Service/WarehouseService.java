package com.saudiMart.Product.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Repository.WarehouseRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Page<Warehouse> getAllWarehouses(Pageable pageable) {
        return warehouseRepository.findAll(pageable);
    }

    public Warehouse getWarehouseById(String id) throws ProductException {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ProductException("Warehouse not found with id: " + id));
    }

    public Page<Warehouse> getWarehousesBySeller(Users user, Pageable pageable) {
        return warehouseRepository.findBySeller(user, pageable);
    }

    public Warehouse createWarehouse(Warehouse warehouse) throws ProductException {
        if (warehouse == null) {
            throw new ProductException("Warehouse details cannot be null");
        }
        System.out.println("warehouse:: " + warehouse);
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(String id, Warehouse warehouseDetails) throws ProductException {
        if (warehouseDetails == null) {
            throw new ProductException("Warehouse details cannot be null for update");
        }
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(id);
        if (warehouseOptional.isPresent()) {
            Warehouse warehouse = warehouseOptional.get();
            if (warehouseDetails.getName() != null) {
                warehouse.setName(warehouseDetails.getName());
            }
            if (warehouseDetails.getIsActive() != null) {
                warehouse.setIsActive(warehouseDetails.getIsActive());
            }

            return warehouseRepository.save(warehouse);
        }
        throw new ProductException("Warehouse not found with id: " + id);
    }

    public void deleteWarehouse(String id) throws ProductException {
        if (!warehouseRepository.existsById(id)) {
            throw new ProductException("Warehouse not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    public Page<Warehouse> searchWarehouses(String name, String location, Users seller, Pageable pageable) {
        return warehouseRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrSeller(name, location,
                seller, pageable);
    }
}