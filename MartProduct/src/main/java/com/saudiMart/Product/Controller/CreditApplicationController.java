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
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.CreditApplication;
import com.saudiMart.Product.Model.CreditApplication.CreditApplicationStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.CreditApplicationService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/creditapplications")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    private final UserService userService;

    @Autowired
    public CreditApplicationController(CreditApplicationService creditApplicationService, UserService userService) {
        this.creditApplicationService = creditApplicationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<CreditApplication>>> getAllCreditApplications(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CreditApplication> creditApplications = creditApplicationService.getAllCreditApplications(pageable);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved all credit applications", creditApplications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CreditApplication>> getCreditApplicationById(@PathVariable String id) {
        try {
            CreditApplication creditApplication = creditApplicationService.getCreditApplicationById(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved credit application", creditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<CreditApplication>> createCreditApplication(
            @RequestBody CreditApplication creditApplication) {
        try {
            CreditApplication createdCreditApplication = creditApplicationService
                    .createCreditApplication(creditApplication);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseWrapper<>(201, "Successfully created credit application", createdCreditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CreditApplication>> updateCreditApplication(@PathVariable String id,
            @RequestBody CreditApplication creditApplicationDetails) {
        try {
            CreditApplication updatedCreditApplication = creditApplicationService.updateCreditApplication(id,
                    creditApplicationDetails);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(200, "Successfully updated credit application", updatedCreditApplication));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCreditApplication(@PathVariable String id) {
        try {
            creditApplicationService.deleteCreditApplication(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted credit application", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<Page<CreditApplication>>> getCreditApplicationsByBuyer(
            @PathVariable String userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Users user = userService.getUserById(userId);
            Page<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByBuyer(user,
                    pageable);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(200, "Successfully retrieved credit applications by buyer",
                            creditApplications));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<Page<CreditApplication>>> getCreditApplicationsBySeller(
            @PathVariable String userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Users user = userService.getUserById(userId);
            Page<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsBySeller(user,
                    pageable);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(200, "Successfully retrieved credit applications by seller",
                            creditApplications));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<Page<CreditApplication>>> getCreditApplicationsByStatus(
            @PathVariable CreditApplicationStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByStatus(status,
                pageable);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "Successfully retrieved credit applications by status", creditApplications));
    }

    @GetMapping("/reviewer/{userId}")
    public ResponseEntity<ResponseWrapper<Page<CreditApplication>>> getCreditApplicationsByReviewer(
            @PathVariable String userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Users user = userService.getUserById(userId);
            Page<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByReviewer(user,
                    pageable);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications by reviewer",
                            creditApplications));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    // @GetMapping("/search")
    // public ResponseEntity<ResponseWrapper<Page<CreditApplication>>>
    // searchCreditApplications(
    // @RequestParam(required = false) String buyerId,
    // @RequestParam(required = false) String sellerId,
    // @RequestParam(required = false) CreditApplicationStatus status,
    // @RequestParam(required = false) String reviewerId,
    // @RequestParam(required = false) BigDecimal minRequestedLimit,
    // @RequestParam(required = false) BigDecimal maxRequestedLimit,
    // @RequestParam(required = false) BigDecimal minApprovedLimit,
    // @RequestParam(required = false) BigDecimal maxApprovedLimit,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate applicationStartDate,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate applicationEndDate,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate reviewStartDate,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate reviewEndDate,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate expiryStartDate,
    // @RequestParam(required = false) @DateTimeFormat(iso =
    // DateTimeFormat.ISO.DATE) LocalDate expiryEndDate,
    // @PageableDefault(size = 10) Pageable pageable) {
    // try {
    // Page<CreditApplication> creditApplications =
    // creditApplicationService.searchCreditApplications(
    // buyerId, sellerId, status, reviewerId, minRequestedLimit, maxRequestedLimit,
    // minApprovedLimit, maxApprovedLimit, applicationStartDate, applicationEndDate,
    // reviewStartDate, reviewEndDate, expiryStartDate, expiryEndDate, pageable);
    // return ResponseEntity
    // .ok(new ResponseWrapper<>(200, "Successfully retrieved credit applications",
    // creditApplications));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ResponseWrapper<>(500, "An error occurred: " + e.getMessage(),
    // null));
    // }
    // }
}