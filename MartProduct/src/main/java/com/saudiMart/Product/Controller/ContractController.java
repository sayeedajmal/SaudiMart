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

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.Contract.ContractStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.ContractService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Contract>>> getAllContracts() throws ProductException {
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all contracts", contracts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Contract>> getContractById(@PathVariable Long id) throws ProductException {
        Contract contract = contractService.getContractById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contract", contract));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Contract>> createContract(@RequestBody Contract contract)
            throws ProductException {
        Contract createdContract = contractService.createContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "Successfully created contract", createdContract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Contract>> updateContract(@PathVariable Long id,
            @RequestBody Contract contractDetails) throws ProductException {
        Contract updatedContract = contractService.updateContract(id, contractDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated contract", updatedContract));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteContract(@PathVariable Long id) throws ProductException {
        contractService.deleteContract(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted contract", null));
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<List<Contract>>> getContractsByBuyer(Users user)
            throws ProductException {
        List<Contract> contracts = contractService.getContractsByBuyer(user);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts for buyer", contracts));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<List<Contract>>> getContractsBySeller(Users users)
            throws ProductException {
        List<Contract> contracts = contractService.getContractsBySeller(users);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts for seller", contracts));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<Contract>>> getContractsByStatus(@PathVariable ContractStatus status)
            throws ProductException {
        List<Contract> contracts = contractService.getContractsByStatus(status);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts by status", contracts));
    }
}