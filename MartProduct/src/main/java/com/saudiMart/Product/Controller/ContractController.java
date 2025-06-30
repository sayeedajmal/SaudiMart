package com.saudiMart.Product.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.saudiMart.Product.Model.Contract;
import com.saudiMart.Product.Model.Contract.ContractStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.ContractService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/contracts")
public class ContractController {
    @Autowired
    private UserService userService;

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Contract>>> getAllContracts(
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Contract> contracts = contractService.getAllContracts(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all contracts", contracts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Contract>> getContractById(@PathVariable String id) throws ProductException {
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
    public ResponseEntity<ResponseWrapper<Contract>> updateContract(@PathVariable String id,
            @RequestBody Contract contractDetails) throws ProductException {
        Contract updatedContract = contractService.updateContract(id, contractDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated contract", updatedContract));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteContract(@PathVariable String id) throws ProductException {
        contractService.deleteContract(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted contract", null));
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<Page<Contract>>> getContractsByBuyer(@PathVariable String userId,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Users user = userService.getUserById(userId); // Fetch the User object
        Page<Contract> contracts = contractService.getContractsByBuyer(user, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts for buyer", contracts));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<Page<Contract>>> getContractsBySeller(@PathVariable String userId,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Users seller = userService.getUserById(userId); // Fetch the User object
        Page<Contract> contracts = contractService.getContractsBySeller(seller, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts for seller", contracts));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<Page<Contract>>> getContractsByStatus(@PathVariable ContractStatus status,
            @PageableDefault(size = 10) Pageable pageable)
            throws ProductException {
        Page<Contract> contracts = contractService.getContractsByStatus(status, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contracts by status", contracts));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<Contract>>> searchContracts(
            @RequestParam(required = false) String buyerId,
            @RequestParam(required = false) String sellerId,
            @RequestParam(required = false) String contractNumber,
            @RequestParam(required = false) ContractStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minCreditLimit,
            @RequestParam(required = false) BigDecimal maxCreditLimit,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<Contract> contracts = contractService.searchContracts(buyerId, sellerId, contractNumber, status, startDate,
                endDate, minCreditLimit, maxCreditLimit, pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved contracts based on search criteria", contracts));
    }
}