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

import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.OrderItemService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<OrderItem>>> getAllOrderItems(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<OrderItem> orderItems = orderItemService.getAllOrderItems(pageable);
        ResponseWrapper<Page<OrderItem>> response = new ResponseWrapper<>(200, "Successfully retrieved all order items",
                orderItems);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderItem>> getOrderItemBy(@PathVariable String id) {
        try {
            OrderItem orderItem = orderItemService.getOrderItemBy(id);
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(200, "Successfully retrieved order item",
                    orderItem);
            return ResponseEntity.ok(response);
        } catch (ProductException e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<OrderItem>> createOrderItem(@RequestBody OrderItem orderItem) {
        try {
            OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(201, "Successfully created order item",
                    createdOrderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ProductException e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while creating the order item", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderItem>> updateOrderItem(@PathVariable String id,
            @RequestBody OrderItem orderItemDetails) {
        try {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDetails);
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(200, "Successfully updated order item",
                    updatedOrderItem);
            return ResponseEntity.ok(response);
        } catch (ProductException e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while updating the order item", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrderItem(@PathVariable String id) {
        try {
            orderItemService.deleteOrderItem(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(200, "Successfully deleted order item", null);
            return ResponseEntity.ok(response);
        } catch (ProductException e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while deleting the order item", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<Page<OrderItem>>> getOrderItemsByProduct(@PathVariable String productId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<OrderItem> orderItems = orderItemService.getOrderItemsByProduct(productId, pageable);
            ResponseWrapper<Page<OrderItem>> response = new ResponseWrapper<>(200,
                    "Successfully retrieved order items for product", orderItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving order items by product", e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<OrderItem>>> searchOrderItems(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String variantId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) String shipFromWarehouseId,
            @RequestParam(required = false) OrderItem.OrderItemStatus status,
            @PageableDefault(size = 10) Pageable pageable) throws ProductException {
        Page<OrderItem> orderItems = orderItemService.searchOrderItems(
                orderId,
                productId,
                variantId,
                quantity,
                shipFromWarehouseId,
                status,
                pageable);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "Successfully retrieved order items based on search criteria", orderItems));
    }
}