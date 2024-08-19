package org.thewhitemage13.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreateOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.exception.OrderNotFoundException;
import org.thewhitemage13.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        try {
            orderService.createOrder(createOrderDTO);
            return ResponseEntity.ok("Order created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam String status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok("Order updated");
        }catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with id = %s not found".formatted(orderId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/get-all")
    public List<Order> getAllOrders() {
        return orderService.showAllOrders();
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<String> getOrderById(@RequestParam("orderId") Long orderId) {
        try {
            return ResponseEntity.ok(orderService.showOrderById(orderId).toString());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with id = %s not found".formatted(orderId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
