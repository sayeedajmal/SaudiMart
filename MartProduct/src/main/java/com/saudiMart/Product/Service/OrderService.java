package com.saudiMart.Product.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import com.saudiMart.Product.Repository.UserRepository;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

 @Autowired
 private UserRepository userRepository;

 public Page<Order> getAllOrders(Pageable pageable) {
 return orderRepository.findAll(pageable);
    }

    public Order getOrderById(String id) throws ProductException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByBuyer(Users user) {
 return orderRepository.findByBuyer(user, Pageable.unpaged()).getContent(); // Assuming you might still need a non-paginated version or adjust this logic
    }

    public List<Order> getOrdersBySeller(Users user) {
 return orderRepository.findBySeller(user, Pageable.unpaged()).getContent(); // Assuming you might still need a non-paginated version or adjust this logic
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
 return orderRepository.findByStatus(status, Pageable.unpaged()).getContent(); // Assuming you might still need a non-paginated version or adjust this logic
    }

    public List<Order> getOrdersByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
 return orderRepository.findByCreatedAtBetween(startDate, endDate, Pageable.unpaged()).getContent(); // Assuming you might still need a non-paginated version or adjust this logic
    }

    public Order createOrder(Order order) throws ProductException {
        if (order == null) {
            throw new ProductException("Order cannot be null");
        }
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order orderDetails) throws ProductException {
        if (orderDetails == null) {
            throw new ProductException("Order details cannot be null for update");
        }
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (orderDetails.getShippingAddress() != null)
                order.setShippingAddress(orderDetails.getShippingAddress());
            if (orderDetails.getBillingAddress() != null)
                order.setBillingAddress(orderDetails.getBillingAddress());
            if (orderDetails.getPaymentMethod() != null)
                order.setPaymentMethod(orderDetails.getPaymentMethod());
            if (orderDetails.getReferenceNumber() != null)
                order.setReferenceNumber(orderDetails.getReferenceNumber());
            if (orderDetails.getPurchaseOrderNumber() != null)
                order.setPurchaseOrderNumber(orderDetails.getPurchaseOrderNumber());
            if (orderDetails.getExpectedDeliveryDate() != null)
                order.setExpectedDeliveryDate(orderDetails.getExpectedDeliveryDate());
            if (orderDetails.getActualDeliveryDate() != null)
                order.setActualDeliveryDate(orderDetails.getActualDeliveryDate());
            if (orderDetails.getNotes() != null)
                order.setNotes(orderDetails.getNotes());
            if (orderDetails.getStatus() != null)
                order.setStatus(orderDetails.getStatus());
            if (orderDetails.getSubtotal() != null)
                order.setSubtotal(orderDetails.getSubtotal());
            if (orderDetails.getTaxAmount() != null)
                order.setTaxAmount(orderDetails.getTaxAmount());
            if (orderDetails.getShippingCost() != null)
                order.setShippingCost(orderDetails.getShippingCost());
            if (orderDetails.getDiscountAmount() != null)
                order.setDiscountAmount(orderDetails.getDiscountAmount());
            if (orderDetails.getTotalPrice() != null)
                order.setTotalPrice(orderDetails.getTotalPrice());

            return orderRepository.save(order);
        }
        throw new ProductException("Order not found with id: " + id);
    }

    public void deleteOrder(String id) throws ProductException {
        if (!orderRepository.existsById(id)) {
            throw new ProductException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}