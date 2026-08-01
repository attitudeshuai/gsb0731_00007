package com.supermarket.controller;

import com.supermarket.model.PurchaseOrder;
import com.supermarket.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping
    public List<PurchaseOrder> getAllOrders(@RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            return purchaseOrderRepository.findByStatus(status);
        }
        return purchaseOrderRepository.findAll();
    }

    @PostMapping
    public PurchaseOrder createOrder(@RequestBody PurchaseOrder order) {
        return purchaseOrderRepository.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrder orderDetails) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setSupplier(orderDetails.getSupplier());
        order.setProduct(orderDetails.getProduct());
        order.setQuantity(orderDetails.getQuantity());
        order.setUnitPrice(orderDetails.getUnitPrice());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setStatus(orderDetails.getStatus());

        return ResponseEntity.ok(purchaseOrderRepository.save(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        purchaseOrderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
