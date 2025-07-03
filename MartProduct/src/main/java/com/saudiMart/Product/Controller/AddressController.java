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

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.Address.AddressType;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.AddressService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Address>>> getAllAddresses(
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Address> addresses = addressService.getAllAddresses(pageable);
        return ResponseEntity.ok(
                new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully retrieved all addresses", addresses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Address>> getAddressById(@PathVariable String id) {
        try {
            Address address = addressService.getAddressById(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully retrieved address", address));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Address>> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Successfully created address",
                            createdAddress));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Address>> updateAddress(@PathVariable String id,
            @RequestBody Address addressDetails) {
        try {
            Address updatedAddress = addressService.updateAddress(id, addressDetails);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully updated address", updatedAddress));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteAddress(@PathVariable String id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully deleted address", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper<Page<Address>>> getAddressesByUserId(@RequestParam String userId,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Users userById = userService.getUserById(userId);
        Page<Address> addresses = addressService.getAddressesByUser(userById, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                "Successfully retrieved addresses for user", addresses));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<Address>>> searchAddresses(
            @RequestParam(required = false) AddressType addressType,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String country,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Address> addresses = addressService.searchAddresses(
                    addressType,
                    companyName,
                    city,
                    state,
                    postalCode,
                    country,
                    pageable);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Addresses found", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "An error occurred during search", null));
        }
    }
}