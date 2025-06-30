package com.saudiMart.Product.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.OrderApproval.OrderApprovalStatus;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.OrderApprovalRepository;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Repository.UserRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderApprovalService {

    @Autowired
    private OrderApprovalRepository orderApprovalRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<OrderApproval> getAllOrderApprovals(Pageable pageable) {
        return orderApprovalRepository.findAll(pageable);
    }

    public OrderApproval getOrderApprovalBy(String id) throws ProductException {
        return orderApprovalRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order Approval not found with : " + id));
    }

    public OrderApproval createOrderApproval(OrderApproval orderApproval) throws ProductException {
        if (orderApproval == null) {
            throw new ProductException("Order Approval cannot be null");
        }
        return orderApprovalRepository.save(orderApproval);
    }

    public OrderApproval updateOrderApproval(String id, OrderApproval orderApprovalDetails) throws ProductException {
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

    public void deleteOrderApproval(String id) throws ProductException {
        if (!orderApprovalRepository.existsById(id)) {
            throw new ProductException("Order Approval not found with : " + id);
        }
        orderApprovalRepository.deleteById(id);
    }

    public Page<OrderApproval> getOrderApprovalsByOrder(Order order, Pageable pageable) throws ProductException {
        return orderApprovalRepository.findByOrder(order, pageable);
    }

    public Page<OrderApproval> getOrderApprovalsByApprover(Users approver, Pageable pageable)
            throws ProductException {
        return orderApprovalRepository.findByApprover(approver, pageable);
    }

    public Page<OrderApproval> getOrderApprovalsByStatus(OrderApprovalStatus status, Pageable pageable) {
        return orderApprovalRepository.findByStatus(status, pageable);
    }

    public Page<OrderApproval> searchOrderApprovals(
            String orderId,
            String approverId,
            Integer approvalLevel,
            OrderApprovalStatus status,
            LocalDateTime minApprovalDate,
            LocalDateTime maxApprovalDate,
            LocalDateTime minCreatedAt,
            LocalDateTime maxCreatedAt,
            Pageable pageable) throws ProductException {

        Order order = null;
        if (orderId != null) {
            order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ProductException("Order not found with id: " + orderId));
        }

        Users approver = null;
        if (approverId != null) {
            approver = userRepository.findById(approverId)
                    .orElseThrow(() -> new ProductException("User not found with id: " + approverId));
        }

        return orderApprovalRepository.searchOrderApprovals(
                order,
                approver,
                approvalLevel,
                status,
                minApprovalDate,
                maxApprovalDate,
                minCreatedAt,
                maxCreatedAt,
                pageable);
    }
}