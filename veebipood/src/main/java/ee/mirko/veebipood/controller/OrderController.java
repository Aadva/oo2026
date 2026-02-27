package ee.mirko.veebipood.controller;

import ee.mirko.veebipood.dto.OrderRowDto;
import ee.mirko.veebipood.entity.Order;
import ee.mirko.veebipood.entity.OrderRow;
import ee.mirko.veebipood.repository.OrderRepository;
import ee.mirko.veebipood.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepository;
    private OrderService orderService;


    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @DeleteMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
        return orderRepository.findAll();
    }


    @PostMapping("orders")
    public Order addOrder(@RequestParam Long personId,
                          @RequestParam(required = false) String parcelMachine,
                          @RequestBody List<OrderRowDto> orderRows){
        return orderService.saveOrder(personId, parcelMachine, orderRows);

    }
}