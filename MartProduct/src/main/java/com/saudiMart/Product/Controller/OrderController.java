package com.saudiMart.Product.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Service.OrderService;
import com.saudiMart.Product.Service.UserService; 
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService; 

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Order>>> getAllOrders(@PageableDefault(size = 10) Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved all orders", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Order>> getOrderById(@PathVariable String id) throws ProductException {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved order", order));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Order>> createOrder(@RequestBody Order order) throws ProductException {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "Successfully created order", createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Order>> updateOrder(@PathVariable String id, @RequestBody Order orderDetails)
            throws ProductException {
        Order updatedOrder = orderService.updateOrder(id, orderDetails);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully updated order", updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrder(@PathVariable String id) throws ProductException {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully deleted order", null));
    }

    @GetMapping("/buyer/{userId}")
    public ResponseEntity<ResponseWrapper<List<Order>>> getOrdersByBuyer(@PathVariable String userId)
            throws ProductException {
        Users buyer = userService.getUserById(userId); 
        List<Order> orders = orderService.getOrdersByBuyer(buyer);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved orders by buyer", orders));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<ResponseWrapper<List<Order>>> getOrdersBySeller(@PathVariable String userId)
            throws ProductException {
        Users seller = userService.getUserById(userId); 
        List<Order> orders = orderService.getOrdersBySeller(seller);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved orders by seller", orders));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<Order>>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved orders by status", orders));
    }

    @GetMapping("/date")
    public ResponseEntity<ResponseWrapper<List<Order>>> getOrdersByCreationDateBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Order> orders = orderService.getOrdersByCreationDateBetween(startDate, endDate);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Successfully retrieved orders by date range", orders));
    }
}