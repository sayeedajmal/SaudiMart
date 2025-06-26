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
import com.saudiMart.Product.Model.ContractItem;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.ContractItemService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/contractitems")
public class ContractItemController {

    @Autowired
    private ContractItemService contractItemService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ContractItem>>> getAllContractItems() throws ProductException {
        List<ContractItem> contractItems = contractItemService.getAllContractItems();
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved all contract items", contractItems));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ContractItem>> getContractItemById(@PathVariable String id)
            throws ProductException {
        ContractItem contractItem = contractItemService.getContractItemById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved contract item", contractItem));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ContractItem>> createContractItem(@RequestBody ContractItem contractItem)
            throws ProductException {
        ContractItem createdContractItem = contractItemService.createContractItem(contractItem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "Successfully created new contract item", createdContractItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ContractItem>> updateContractItem(@PathVariable String id,
            @RequestBody ContractItem contractItemDetails) throws ProductException {
        ContractItem updatedContractItem = contractItemService.updateContractItem(id, contractItemDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated contract item", updatedContractItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteContractItem(@PathVariable String id) throws ProductException {
        contractItemService.deleteContractItem(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted contract item", null));
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<ResponseWrapper<List<ContractItem>>> getContractItemsByContract(
            Contract contract) throws ProductException {
        List<ContractItem> contractItems = contractItemService.getContractItemsByContract(contract);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by contract ID", contractItems));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<List<ContractItem>>> getContractItemsByProduct(
            Products product) throws ProductException {
        List<ContractItem> contractItems = contractItemService.getContractItemsByProduct(product);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by product ID", contractItems));
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ResponseWrapper<List<ContractItem>>> getContractItemsByVariant(
            ProductVariant variant) throws ProductException {
        List<ContractItem> contractItems = contractItemService.getContractItemsByVariant(variant);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by variant ID", contractItems));
    }
}