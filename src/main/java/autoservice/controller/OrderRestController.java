package autoservice.controller;

import autoservice.entity.Order;
import autoservice.repository.OrderRepository;
import autoservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<Order> listAllOrders(@RequestParam(value = "keyword", required = false) String keyword) {
        return orderService.listAll(keyword);
    }


    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable Long id) {
        return orderService.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }



    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        if (order.getOrderDate() == null) {
            return ResponseEntity.badRequest().body("Дата заказа не может быть пустой.");
        }

        try {
            orderRepository.save(order);


            return ResponseEntity.ok().body("{\"message\": \"Заказ успешно сохранен.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Ошибка при сохранении заказа.\"}");
        }
    }





}
