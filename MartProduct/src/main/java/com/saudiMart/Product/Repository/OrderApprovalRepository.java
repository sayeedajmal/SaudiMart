package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderApproval;
import com.saudiMart.Product.Model.OrderApproval.OrderApprovalStatus;
import com.saudiMart.Product.Model.Users;

@Repository
public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {

    List<OrderApproval> findByOrder(Order order);

    List<OrderApproval> findByApprover(Users approver);

    List<OrderApproval> findByStatus(OrderApprovalStatus status);

}