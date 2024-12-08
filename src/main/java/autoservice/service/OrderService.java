package autoservice.service;

import autoservice.entity.Client;
import autoservice.entity.Order;
import autoservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listAll(String keyword) {
        if (keyword != null) {
            return orderRepository.search(keyword);
        }
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findOrdersByClientId(Long clientId) {
        return orderRepository.findAllByClientId(clientId);
    }

    public List<Order> getAllOrder(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return orderRepository.findAll();
        }
        return orderRepository.search(keyword);
    }
}
