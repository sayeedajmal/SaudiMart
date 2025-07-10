package com.saudiMart.Product.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Service.InventoryService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Service.WarehouseService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Inventory>>> getAllInventory(
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Page<Inventory> inventory = inventoryService.getAllInventory(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all inventory entries", inventory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Inventory>> getInventoryById(@PathVariable String id)
            throws ProductException {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entry", inventory));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Inventory>> createInventory(@RequestBody Inventory inventory)
            throws ProductException {
        Inventory createdInventory = inventoryService.createInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "Successfully created inventory entry", createdInventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Inventory>> updateInventory(@PathVariable String id,
            @RequestBody Inventory inventoryDetails) throws ProductException {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated inventory entry", updatedInventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteInventory(@PathVariable String id) throws ProductException {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted inventory entry", null));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<Page<Inventory>>> getInventoryByProductId(@PathVariable String productId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Inventory> inventory = inventoryService.getInventoryByProductId(productId, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for product", inventory));
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ResponseWrapper<Inventory>> getInventoryByVariantId(@PathVariable String variantId) throws ProductException {

        Inventory inventory = inventoryService.getInventoryByVariantId(variantId);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for variant", inventory));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ResponseWrapper<Inventory>> getInventoryByWarehouseId(@PathVariable String warehouseId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Inventory inventory = inventoryService.getInventoryByWarehouseId(warehouseId);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for warehouse", inventory));
    }

    @GetMapping("/sellerinventory/{userId}")
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getInventoryForSeller(@PathVariable String userId,
            Pageable pageable) throws ProductException {

        Users user = userService.getUserById(userId);

        Page<Warehouse> sellersWarehouses = warehouseService.getWarehousesBySeller(user, pageable);
        List<Inventory> sellerInventory = new ArrayList<>();

        sellersWarehouses.forEach(warehouse -> {
            sellerInventory.addAll(inventoryService.getInventoryByWarehouse(warehouse, pageable).getContent());
        });

        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory for seller", sellerInventory));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<Inventory>>> searchInventory(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String variantId,
            @RequestParam(required = false) String warehouseId,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity,
            @RequestParam(required = false) Integer minReservedQuantity,
            @RequestParam(required = false) Integer maxReservedQuantity,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Inventory> inventory = inventoryService.searchInventory(productId, variantId, warehouseId, minQuantity,
                    maxQuantity, minReservedQuantity, maxReservedQuantity, pageable);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory based on criteria", inventory));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }
}