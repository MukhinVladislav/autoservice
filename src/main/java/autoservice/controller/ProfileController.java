package autoservice.controller;

import autoservice.entity.Client;
import autoservice.entity.Order;
import autoservice.entity.User;
import autoservice.service.ClientService;
import autoservice.service.OrderService;
import autoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        if (user != null) {
            Optional<Client> client = clientService.findByUser(user);
            Client c = client.orElseThrow(() -> new RuntimeException("Client not found"));
            List<Order> orders = orderService.findOrdersByClientId(c.getId());


            if (orders != null && !orders.isEmpty()) {
                for (Order order : orders) {
                    System.out.println("Found order: " + order.toString());
                }
            } else {
                System.out.println("No orders found for client " + c.getId());
            }

            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            model.addAttribute("client", client);
        }

        return "profile";
    }


    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Client client, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        if (user != null) {

            Optional<Client> existingClient = clientService.findById(user.getU01_ID());
            if (existingClient.isPresent()) {
                Client clientToUpdate = existingClient.get();

                clientToUpdate.setName(client.getName());
                clientToUpdate.setPhone(client.getPhone());
                clientToUpdate.setEmail(client.getEmail());
                clientService.save(clientToUpdate);
            }
        }
        return "redirect:/profile";
    }
}


