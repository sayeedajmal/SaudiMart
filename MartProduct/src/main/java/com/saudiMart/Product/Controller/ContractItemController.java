package com.saudiMart.Product.Controller;

import java.math.BigDecimal;

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
        public ResponseEntity<ResponseWrapper<Page<ContractItem>>> getAllContractItems(
                        @PageableDefault(size = 10) Pageable pageable) throws ProductException {
                Page<ContractItem> contractItems = contractItemService.getAllContractItems(pageable);
                return ResponseEntity
                                .ok(new ResponseWrapper<>(200, "Successfully retrieved all contract items",
                                                contractItems));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseWrapper<ContractItem>> getContractItemById(@PathVariable String id)
                        throws ProductException {
                ContractItem contractItem = contractItemService.getContractItemById(id);
                return ResponseEntity
                                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract item", contractItem));
        }

        @PostMapping
        public ResponseEntity<ResponseWrapper<ContractItem>> createContractItem(@RequestBody ContractItem contractItem)
                        throws ProductException {
                ContractItem createdContractItem = contractItemService.createContractItem(contractItem);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseWrapper<>(201, "Successfully created new contract item",
                                                createdContractItem));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseWrapper<ContractItem>> updateContractItem(@PathVariable String id,
                        @RequestBody ContractItem contractItemDetails) throws ProductException {
                ContractItem updatedContractItem = contractItemService.updateContractItem(id, contractItemDetails);
                return ResponseEntity.ok(
                                new ResponseWrapper<>(200, "Successfully updated contract item", updatedContractItem));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseWrapper<Void>> deleteContractItem(@PathVariable String id)
                        throws ProductException {
                contractItemService.deleteContractItem(id);
                return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted contract item", null));
        }

        @GetMapping("/contract/{contractId}")
        public ResponseEntity<ResponseWrapper<Page<ContractItem>>> getContractItemsByContract(
                        @PathVariable String contractId,
                        @PageableDefault(size = 10) Pageable pageable) throws ProductException {
                Contract contract = new Contract();
                contract.setId(contractId);
                Page<ContractItem> contractItems = contractItemService.getContractItemsByContract(contract, pageable);
                return ResponseEntity
                                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by contract ID",
                                                contractItems));
        }

        @GetMapping("/product/{productId}")
        public ResponseEntity<ResponseWrapper<Page<ContractItem>>> getContractItemsByProduct(
                        @PathVariable String productId,
                        @PageableDefault(size = 10) Pageable pageable) throws ProductException {
                Products product = new Products();
                product.setId(productId);
                Page<ContractItem> contractItems = contractItemService.getContractItemsByProduct(product, pageable);
                return ResponseEntity
                                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by product ID",
                                                contractItems));
        }

        @GetMapping("/variant/{variantId}")
        public ResponseEntity<ResponseWrapper<Page<ContractItem>>> getContractItemsByVariant(
                        @PathVariable String variantId,
                        @PageableDefault(size = 10) Pageable pageable) throws ProductException {
                ProductVariant variant = new ProductVariant();
                variant.setId(variantId);
                Page<ContractItem> contractItems = contractItemService.getContractItemsByVariant(variant, pageable);
                return ResponseEntity
                                .ok(new ResponseWrapper<>(200, "Successfully retrieved contract items by variant ID",
                                                contractItems));
        }

        @GetMapping("/search")
        public ResponseEntity<ResponseWrapper<Page<ContractItem>>> searchContractItems(
                        @RequestParam(required = false) String contractId,
                        @RequestParam(required = false) String productId,
                        @RequestParam(required = false) String variantId,
                        @RequestParam(required = false) BigDecimal minNegotiatedPrice,
                        @RequestParam(required = false) BigDecimal maxNegotiatedPrice,
                        @RequestParam(required = false) Integer minCommitmentQuantity,
                        @RequestParam(required = false) Integer maxQuantity,
                        @RequestParam(required = false) BigDecimal minDiscountPercent,
                        @RequestParam(required = false) BigDecimal maxDiscountPercent,
                        @RequestParam(required = false) Boolean isActive,
                        @PageableDefault(size = 10) Pageable pageable) throws ProductException {
                Page<ContractItem> contractItems = contractItemService.searchContractItems(
                                contractId, productId, variantId, minNegotiatedPrice, maxNegotiatedPrice,
                                minCommitmentQuantity, maxQuantity, minDiscountPercent, maxDiscountPercent, isActive,
                                pageable);
                return ResponseEntity.ok(new ResponseWrapper<>(200,
                                "Successfully retrieved contract items based on search criteria", contractItems));
        }
}