
package autoservice.controller;

import autoservice.entity.Client;
import autoservice.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping
    public String viewClient(Model model, HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Client> listClient = clientService.listAll(keyword);
        model.addAttribute("listClient", listClient);
        model.addAttribute("keyword", keyword);


        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "client";
    }
    @GetMapping("/search")
    public String searchClient(Model model, @RequestParam(required = false) String keyword) {
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }

        List<Client> client = clientService.getAllClient(keyword);
        model.addAttribute("client", client);
        return "index";
    }


    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/client";
    }

}