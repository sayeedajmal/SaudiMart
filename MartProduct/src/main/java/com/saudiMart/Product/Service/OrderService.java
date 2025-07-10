package com.saudiMart.Product.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.Order.OrderStatus;
import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Quote;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Utils.ProductException;

import jakarta.validation.NoProviderFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductVariantService variantService;

    @Autowired
    private QuoteService quoteService;

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(String id) throws ProductException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByBuyer(Users user) {
        return orderRepository.findByBuyer(user, Pageable.unpaged()).getContent();
    }

    public List<Order> getOrdersBySeller(Users user) {
        return orderRepository.findBySeller(user, Pageable.unpaged()).getContent();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status, Pageable.unpaged()).getContent();
    }

    public List<Order> getOrdersByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate, Pageable.unpaged()).getContent();
    }

    public Order createOrder(Order order) throws ProductException {
        if (order == null) {
            throw new ProductException("Order cannot be null");
        }

        order.getOrderItem().forEach(item -> {
            try {
                ProductVariant variantById = variantService.getProductVariantById(item.getVariant().getId());
                Quote quoteById = quoteService.getQuoteById(order.getQuote().getId());
                Optional<PriceTier> matchingTier = variantById.getPriceTiers().stream()
                        .filter(tier -> item.getQuantity() >= tier.getMinQuantity()
                                && item.getQuantity() <= tier.getMaxQuantity())
                        .max(Comparator.comparingInt(PriceTier::getMaxQuantity)); // Closest to maxQuantity

                PriceTier selectedTier = matchingTier.orElseThrow(
                        () -> new ProductException("No price tier found for quantity: " + item.getQuantity()));
                order.setStatus(OrderStatus.DRAFT);
                order.setSubtotal(quoteById.getSubtotal());
                order.setShippingCost(BigDecimal.ZERO);
                order.setDiscountPercent(selectedTier.getDiscountPercent());
                order.setTaxAmount(quoteById.getTaxAmount());
                order.setTotalPrice(quoteById.getQuoteItem().getTotalPrice());
            } catch (ProductException e) {
                throw new NoProviderFoundException("Product Variant not found with id: " + item.getVariant().getId());
            }
        });

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
            if (orderDetails.getExpectedDeliveryDate() != null)
                order.setExpectedDeliveryDate(orderDetails.getExpectedDeliveryDate());
            if (orderDetails.getActualDeliveryDate() != null)
                order.setActualDeliveryDate(orderDetails.getActualDeliveryDate());
            if (orderDetails.getNotes() != null)
                order.setNotes(orderDetails.getNotes());
            if (orderDetails.getStatus() != null)
                order.setStatus(orderDetails.getStatus());
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