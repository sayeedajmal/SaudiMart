package com.saudiMart.Product.Controller;

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

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.AddressService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Address>>> getAllAddresses() throws ProductException {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(
                new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully retrieved all addresses", addresses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Address>> getAddressById(@PathVariable Long id) {
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
    public ResponseEntity<ResponseWrapper<Address>> updateAddress(@PathVariable Long id,
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
    public ResponseEntity<ResponseWrapper<Void>> deleteAddress(@PathVariable Long id) {
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<List<Address>>> getAddressesByUserId(Users user) throws ProductException {
        try {
            List<Address> addresses = addressService.getAddressesByUser(user);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Successfully retrieved addresses for user", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }
}