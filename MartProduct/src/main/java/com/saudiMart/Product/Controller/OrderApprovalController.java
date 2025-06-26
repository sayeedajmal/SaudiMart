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

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.OrderApproval.OrderApprovalStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.OrderApprovalService;
import com.saudiMart.Product.Service.OrderService;
import com.saudiMart.Product.Service.UserService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/orderapprovals")
public class OrderApprovalController {

    @Autowired
    private OrderApprovalService orderApprovalService;

    @Autowired
    private OrderService orderService; // Assuming an OrderService exists to get Order objects

    @Autowired
    private UserService userService; // Assuming a UserService exists to get User objects

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<OrderApproval>>> getAllOrderApprovals() {
        List<OrderApproval> orderApprovals = orderApprovalService.getAllOrderApprovals();
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved all order approvals", orderApprovals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderApproval>> getOrderApprovalById(@PathVariable String id) {
        try {
            OrderApproval orderApproval = orderApprovalService.getOrderApprovalBy(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully retrieved order approval", orderApproval));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<OrderApproval>> createOrderApproval(
            @RequestBody OrderApproval orderApproval) {
        try {
            OrderApproval createdOrderApproval = orderApprovalService.createOrderApproval(orderApproval);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Successfully created order approval",
                            createdOrderApproval));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderApproval>> updateOrderApproval(@PathVariable String id,
            @RequestBody OrderApproval orderApprovalDetails) {
        try {
            OrderApproval updatedOrderApproval = orderApprovalService.updateOrderApproval(id, orderApprovalDetails);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(200, "Successfully updated order approval", updatedOrderApproval));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrderApproval(@PathVariable String id) {
        try {
            orderApprovalService.deleteOrderApproval(id);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted order approval", null));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseWrapper<List<OrderApproval>>> getOrderApprovalsByOrderId(
            @PathVariable String orderId) {
        try {
            Order order = orderService.getOrderById(orderId); // Assuming OrderService has getOrderById
            List<OrderApproval> orderApprovals = orderApprovalService.getOrderApprovalsByOrder(order);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(200, "Successfully retrieved order approvals by order ID", orderApprovals));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/approver/{approverId}")
    public ResponseEntity<ResponseWrapper<List<OrderApproval>>> getOrderApprovalsByApproverId(
            @PathVariable String approverId) {
        try {
            // Assuming Users has an ID of type String and UserService has getUserById
            Users approver = userService.getUserById(approverId);
            List<OrderApproval> orderApprovals = orderApprovalService.getOrderApprovalsByApprover(approver);
            return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved order approvals by approver ID",
                    orderApprovals));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<OrderApproval>>> getOrderApprovalsByStatus(
            @PathVariable OrderApprovalStatus status) {
        List<OrderApproval> orderApprovals = orderApprovalService.getOrderApprovalsByStatus(status);
        return ResponseEntity
                .ok(new ResponseWrapper<>(200, "Successfully retrieved order approvals by status", orderApprovals));
    }
}