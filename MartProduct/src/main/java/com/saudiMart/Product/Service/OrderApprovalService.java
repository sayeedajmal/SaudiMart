package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.enums.OrderApprovalStatus; // Assuming you create this enum
import com.saudiMart.Product.Repository.OrderApprovalRepository;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderApprovalService {

    @Autowired
    private OrderApprovalRepository orderApprovalRepository;

    public List<OrderApproval> getAllOrderApprovals() {
        return orderApprovalRepository.findAll();
    }

    public OrderApproval getOrderApprovalById(Long id) throws ProductException {
        return orderApprovalRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order Approval not found with id: " + id));
    }

    public OrderApproval createOrderApproval(OrderApproval orderApproval) throws ProductException {
        if (orderApproval == null) {
            throw new ProductException("Order Approval cannot be null");
        }
        return orderApprovalRepository.save(orderApproval);
    }

    public OrderApproval updateOrderApproval(Long id, OrderApproval orderApprovalDetails) throws ProductException {
        if (orderApprovalDetails == null) {
            throw new ProductException("Order Approval details cannot be null for update");
        }
        Optional<OrderApproval> orderApprovalOptional = orderApprovalRepository.findById(id);
        if (orderApprovalOptional.isPresent()) {
            OrderApproval orderApproval = orderApprovalOptional.get();
            if (orderApprovalDetails.getApprovalLevel() != null)
                orderApproval.setApprovalLevel(orderApprovalDetails.getApprovalLevel());
            if (orderApprovalDetails.getStatus() != null)
                orderApproval.setStatus(orderApprovalDetails.getStatus());
            if (orderApprovalDetails.getComments() != null)
                orderApproval.setComments(orderApprovalDetails.getComments());
            if (orderApprovalDetails.getApprovalDate() != null)
                orderApproval.setApprovalDate(orderApprovalDetails.getApprovalDate());
            return orderApprovalRepository.save(orderApproval);
        }
        throw new ProductException("Order Approval not found with id: " + id);
    }

    public void deleteOrderApproval(Long id) throws ProductException {
        if (!orderApprovalRepository.existsById(id)) {
            throw new ProductException("Order Approval not found with id: " + id);
        }
        orderApprovalRepository.deleteById(id);
    }

    public List<OrderApproval> getOrderApprovalsByOrderId(Long orderId) {
        return orderApprovalRepository.findByOrderId(orderId);
    }

    public List<OrderApproval> getOrderApprovalsByApproverId(Long approverId) {
        return orderApprovalRepository.findByApproverId(approverId);
    }

    public List<OrderApproval> getOrderApprovalsByStatus(OrderApprovalStatus status) {
        return orderApprovalRepository.findByStatus(status);
    }
}