package com.saudiMart.Product.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Service.InventoryService;
import com.saudiMart.Product.Service.ProductVariantService;
import com.saudiMart.Product.Service.ProductsService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Service.WarehouseService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getAllInventory() throws ProductException {
        List<Inventory> inventory = inventoryService.getAllInventory();
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
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getInventoryByProductId(@PathVariable String productId)
            throws ProductException {
        Products product = productsService.getProductById(productId);

        List<Inventory> inventory = inventoryService.getInventoryByProduct(product);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for product", inventory));
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getInventoryByVariantId(@PathVariable String variantId)
            throws ProductException {
        ProductVariant variant = productVariantService.getProductVariantById(variantId);

        List<Inventory> inventory = inventoryService.getInventoryByVariant(variant);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for variant", inventory));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getInventoryByWarehouseId(@PathVariable String warehouseId)
            throws ProductException {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);

        List<Inventory> inventory = inventoryService.getInventoryByWarehouse(warehouse);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory entries for warehouse", inventory));
    }

    @GetMapping("/sellerinventory/{userId}")
    public ResponseEntity<ResponseWrapper<List<Inventory>>> getInventoryForSeller(@PathVariable String userId) {
        try {

            Users user = userService.getUserById(userId);

            List<Warehouse> sellersWarehouses = warehouseService.getWarehousesBySeller(user);

            List<Inventory> sellerInventory = new ArrayList<>();
            for (Warehouse warehouse : sellersWarehouses) {
                List<Inventory> warehouseInventory = inventoryService.getInventoryByWarehouse(warehouse);
                sellerInventory.addAll(warehouseInventory);
            }
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved inventory for seller", sellerInventory));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }
}