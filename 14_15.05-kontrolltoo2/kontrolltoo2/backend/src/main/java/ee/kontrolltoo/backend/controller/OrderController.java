package ee.kontrolltoo.backend.controller;

import ee.kontrolltoo.backend.entity.Order;
import ee.kontrolltoo.backend.entity.OrderRow;
import ee.kontrolltoo.backend.repository.OrderRepository;
import ee.kontrolltoo.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("orders")
    public Order addOrder(@RequestBody List<OrderRow> orderRows) {
        Order order = new Order();
        order.setOrderRows(orderRows);
        order.setTotal(orderService.calculateOrderSum(orderRows));
        return orderRepository.save(order);
    }
}
