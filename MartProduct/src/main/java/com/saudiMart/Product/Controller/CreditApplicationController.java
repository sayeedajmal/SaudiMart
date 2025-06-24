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

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplication.CreditApplicationStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.CreditApplicationService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/creditapplications")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @Autowired
    public CreditApplicationController(CreditApplicationService creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<CreditApplication>>> getAllCreditApplications() {
        List<CreditApplication> creditApplications = creditApplicationService.getAllCreditApplications();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all credit applications", creditApplications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CreditApplication>> getCreditApplicationById(@PathVariable Long id) {
        try {
            CreditApplication creditApplication = creditApplicationService.getCreditApplicationById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved credit application", creditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<CreditApplication>> createCreditApplication(@RequestBody CreditApplication creditApplication) {
        try {
            CreditApplication createdCreditApplication = creditApplicationService.createCreditApplication(creditApplication);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(201, "Successfully created credit application", createdCreditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CreditApplication>> updateCreditApplication(@PathVariable Long id, @RequestBody CreditApplication creditApplicationDetails) {
        try {
            CreditApplication updatedCreditApplication = creditApplicationService.updateCreditApplication(id, creditApplicationDetails);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated credit application", updatedCreditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCreditApplication(@PathVariable Long id) {
        try {
            creditApplicationService.deleteCreditApplication(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted credit application", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<List<CreditApplication>>> getCreditApplicationsByBuyer(@PathVariable Users userId) {
        List<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByBuyer(userId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications by buyer", creditApplications));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<List<CreditApplication>>> getCreditApplicationsBySeller(@PathVariable Users userId) {
        List<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsBySeller(userId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications by seller", creditApplications));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<CreditApplication>>> getCreditApplicationsByStatus(@PathVariable CreditApplicationStatus status) {
        List<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByStatus(status);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications by status", creditApplications));
    }

    @GetMapping("/reviewer/{userId}")
    public ResponseEntity<ResponseWrapper<List<CreditApplication>>> getCreditApplicationsByReviewer(@PathVariable Users userId) {
        List<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByReviewer(userId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications by reviewer", creditApplications));
    }
}