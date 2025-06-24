package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.OrderApproval.OrderApprovalStatus;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.OrderApprovalRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderApprovalService {

    @Autowired
    private OrderApprovalRepository orderApprovalRepository;

    public List<OrderApproval> getAllOrderApprovals() {
        return orderApprovalRepository.findAll();
    }

    public OrderApproval getOrderApprovalBy(Long id) throws ProductException {
        return orderApprovalRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order Approval not found with : " + id));
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
        throw new ProductException("Order Approval not found with : " + id);
    }

    public void deleteOrderApproval(Long id) throws ProductException {
        if (!orderApprovalRepository.existsById(id)) {
            throw new ProductException("Order Approval not found with : " + id);
        }
        orderApprovalRepository.deleteById(id);
    }

    public List<OrderApproval> getOrderApprovalsByOrder(Order order) {
        return orderApprovalRepository.findByOrder(order);
    }

    public List<OrderApproval> getOrderApprovalsByApprover(Users approver) {
        return orderApprovalRepository.findByApprover(approver);
    }

    public List<OrderApproval> getOrderApprovalsByStatus(OrderApprovalStatus status) {
        return orderApprovalRepository.findByStatus(status);
    }
}