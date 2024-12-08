package autoservice.controller;

import autoservice.service.OrderService;
import autoservice.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public String viewOrders(Model model, HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Order> listOrder = orderService.listAll(keyword);
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("keyword", keyword);


        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }

        return "order";
    }

    @GetMapping("/search")
    public String searchOrder(Model model, @RequestParam(required = false) String keyword) {
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }

        List<Order> order = orderService.getAllOrder(keyword);
        model.addAttribute("order", order);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/order";
    }
}
