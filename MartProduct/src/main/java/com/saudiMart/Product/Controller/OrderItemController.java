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
import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.OrderItemService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<OrderItem>>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        ResponseWrapper<List<OrderItem>> response = new ResponseWrapper<>(200, "Successfully retrieved all order items", orderItems);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderItem>> getOrderItemBy(@PathVariable Long id) {
        try {
            OrderItem orderItem = orderItemService.getOrderItemBy(id);
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(200, "Successfully retrieved order item", orderItem);
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
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(201, "Successfully created order item", createdOrderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ProductException e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
             ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while creating the order item", null);
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderItem>> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItemDetails) {
        try {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDetails);
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(200, "Successfully updated order item", updatedOrderItem);
            return ResponseEntity.ok(response);
        } catch (ProductException e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseWrapper<OrderItem> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while updating the order item", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrderItem(@PathVariable Long id) {
        try {
            orderItemService.deleteOrderItem(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(200, "Successfully deleted order item", null);
            return ResponseEntity.ok(response);
        } catch (ProductException e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while deleting the order item", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseWrapper<List<OrderItem>>> getOrderItemsByOrder(@PathVariable Long orderId) {
        // Assuming you have a method in OrderService to get an Order by ID
        // and then pass that Order object to orderItemService.getOrderItemsByOrder
        // For now, let's assume OrderService can return an Order by ID
        try {
            Order order = new Order(); // Placeholder - replace with actual Order retrieval logic
            order.setId(orderId); // Set the ID for the placeholder Order

            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);
            ResponseWrapper<List<OrderItem>> response = new ResponseWrapper<>(200, "Successfully retrieved order items for order", orderItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<OrderItem>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while retrieving order items by order", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<List<OrderItem>>> getOrderItemsByProduct(@PathVariable Long productId) {
         // Assuming you have a method in ProductsService to get a Product by ID
        // and then pass that Products object to orderItemService.getOrderItemsByProduct
        // For now, let's assume ProductsService can return a Product by ID
        try {
            Products product = new Products(); // Placeholder - replace with actual Product retrieval logic
            product.setId(productId); // Set the ID for the placeholder Product

            List<OrderItem> orderItems = orderItemService.getOrderItemsByProduct(product);
            ResponseWrapper<List<OrderItem>> response = new ResponseWrapper<>(200, "Successfully retrieved order items for product", orderItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
             ResponseWrapper<List<OrderItem>> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while retrieving order items by product", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}