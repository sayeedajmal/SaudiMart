package com.saudiMart.Product.Repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.OrderApproval.OrderApprovalStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface OrderApprovalRepository extends JpaRepository<OrderApproval, String> {

    Page<OrderApproval> findByOrder(Order order, Pageable pageable);

    Page<OrderApproval> findByApprover(Users approver, Pageable pageable);

    Page<OrderApproval> findByStatus(OrderApprovalStatus status, Pageable pageable);

    Page<OrderApproval> searchOrderApprovals(Order order, Users approver, Integer approvalLevel,
            OrderApprovalStatus status, LocalDateTime minApprovalDate, LocalDateTime maxApprovalDate,
            LocalDateTime minCreatedAt, LocalDateTime maxCreatedAt, Pageable pageable);

}