package com.saudiMart.Product.Controller;

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

import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Model.Warehouse;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Service.WarehouseService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    private final UserService userService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService, UserService userService) {
        this.warehouseService = warehouseService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Warehouse>>> searchWarehouses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String sellerId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Users seller = userService.getUserById(sellerId);
        Page<Warehouse> warehouses = warehouseService.searchWarehouses(name, location, seller, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all warehouses", warehouses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Warehouse>> getWarehouseById(@PathVariable String id) {
        try {
            Warehouse warehouse = warehouseService.getWarehouseById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved warehouse", warehouse));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Warehouse>> createWarehouse(@RequestBody Warehouse warehouse) {
        try {
            Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(201, "Successfully created warehouse", createdWarehouse));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Warehouse>> updateWarehouse(@PathVariable String id,
            @RequestBody Warehouse warehouseDetails) {
        try {
            Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, warehouseDetails);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated warehouse", updatedWarehouse));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteWarehouse(@PathVariable String id) {
        try {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted warehouse", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred", null));
        }
    }

    @GetMapping("/seller")
    public ResponseEntity<ResponseWrapper<Page<Warehouse>>> getWarehousesBySeller(@RequestParam String sellerId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Users user = userService.getUserById(sellerId);
        Page<Warehouse> warehouses = warehouseService.getWarehousesBySeller(user, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved warehouses by seller", warehouses));
    }

}