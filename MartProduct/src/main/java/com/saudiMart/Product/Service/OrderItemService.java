package com.saudiMart.Product.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Order;
import com.saudiMart.Product.Model.OrderItem;
import com.saudiMart.Product.Model.OrderItem.OrderItemStatus;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.OrderItemRepository;
import com.saudiMart.Product.Repository.OrderRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Repository.WarehouseRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Page<OrderItem> getAllOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable);
    }

    public OrderItem getOrderItemBy(String id) throws ProductException {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ProductException("Order item not found with id: " + id));
    }

    public Page<OrderItem> getOrderItemsByProduct(String productId, Pageable pageable) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return orderItemRepository.findByProduct(product, pageable);
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
            if (orderItemDetails.getShipFromWarehouse() != null)
                orderItem.setShipFromWarehouse(orderItemDetails.getShipFromWarehouse());
            if (orderItemDetails.getStatus() != null)
                orderItem.setStatus(orderItemDetails.getStatus());
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

    public Page<OrderItem> searchOrderItems(
            String orderId,
            String productId,
            String variantId,
            Integer quantity,
            String shipFromWarehouseId,
            OrderItemStatus status,
            Pageable pageable) throws ProductException {

        Specification<OrderItem> spec = Specification.where(null);

        if (orderId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ProductException("Order not found with id: " + orderId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("order"), order));
        }

        if (productId != null) {
            Products product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("product"), product));
        }

        if (variantId != null) {
            com.saudiMart.Product.Model.ProductVariant variant = productVariantRepository.findById(variantId)
                    .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("variant"), variant));
        }

        if (quantity != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("quantity"), quantity));
        }

        if (shipFromWarehouseId != null) {
            com.saudiMart.Product.Model.Warehouse warehouse = warehouseRepository.findById(shipFromWarehouseId)
                    .orElseThrow(() -> new ProductException("Warehouse not found with id: " + shipFromWarehouseId));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("shipFromWarehouse"), warehouse));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        return orderItemRepository.findAll(spec, pageable);
    }
}