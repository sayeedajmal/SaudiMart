package com.saudiMart.Product.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderStatus;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) throws ProductException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    public List<Order> getOrdersBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public Order createOrder(Order order) throws ProductException {
        if (order == null) {
            throw new ProductException("Order cannot be null");
        }
        // Additional validation or business logic can be added here before saving
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) throws ProductException {
        if (orderDetails == null) {
            throw new ProductException("Order details cannot be null for update");
        }
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            // Update fields based on orderDetails
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

            // Relationships like buyer, seller, contract should ideally not be changed in an update method like this
            // If needed, separate methods for handling relationship updates should be considered.

            return orderRepository.save(order);
        }
        throw new ProductException("Order not found with id: " + id);
    }

    public void deleteOrder(Long id) throws ProductException {
        if (!orderRepository.existsById(id)) {
            throw new ProductException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}