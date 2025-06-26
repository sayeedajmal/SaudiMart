package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.OrderItemRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemBy(String id) throws ProductException {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order item not found with id: " + id));
    }

    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public List<OrderItem> getOrderItemsByProduct(Products product) {
        return orderItemRepository.findByProduct(product);
    }

    public OrderItem createOrderItem(OrderItem orderItem) throws ProductException {
        if (orderItem == null) {
            throw new ProductException("Order item cannot be null");
        }
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(String id, OrderItem orderItemDetails) throws ProductException {
        if (orderItemDetails == null) {
            throw new ProductException("Order item details cannot be null for update");
        }
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);
        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            if (orderItemDetails.getQuantity() != null)
                orderItem.setQuantity(orderItemDetails.getQuantity());
            if (orderItemDetails.getPricePerUnit() != null)
                orderItem.setPricePerUnit(orderItemDetails.getPricePerUnit());
            if (orderItemDetails.getDiscountPercent() != null)
                orderItem.setDiscountPercent(orderItemDetails.getDiscountPercent());
            if (orderItemDetails.getTaxPercent() != null)
                orderItem.setTaxPercent(orderItemDetails.getTaxPercent());
            if (orderItemDetails.getTotalPrice() != null)
                orderItem.setTotalPrice(orderItemDetails.getTotalPrice());
            if (orderItemDetails.getShipFromWarehouse() != null)
                orderItem.setShipFromWarehouse(orderItemDetails.getShipFromWarehouse());
            if (orderItemDetails.getStatus() != null)
                orderItem.setStatus(orderItemDetails.getStatus());
            if (orderItemDetails.getNotes() != null)
                orderItem.setNotes(orderItemDetails.getNotes());

            if (orderItemDetails.getOrder() != null)
                orderItem.setOrder(orderItemDetails.getOrder());
            if (orderItemDetails.getProduct() != null)
                orderItem.setProduct(orderItemDetails.getProduct());
            if (orderItemDetails.getVariant() != null)
                orderItem.setVariant(orderItemDetails.getVariant());
            return orderItemRepository.save(orderItem);
        }
        throw new ProductException("Order item not found with id: " + id);
    }

    public void deleteOrderItem(String id) throws ProductException {
        if (!orderItemRepository.existsById(id)) {
            throw new ProductException("Order item not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }
}