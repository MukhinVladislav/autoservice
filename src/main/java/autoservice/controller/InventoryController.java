package autoservice.controller;

import autoservice.entity.Client;
import autoservice.entity.Inventory;
import autoservice.service.InventoryService;


import autoservice.entity.Inventory;
import autoservice.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping
    public String viewInventory(Model model, HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Inventory> listInventory = inventoryService.listAll(keyword);
        model.addAttribute("listInventory", listInventory);
        model.addAttribute("keyword", keyword);


        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "inventory";
    }

    @GetMapping("/search")
    public String searchInventory(Model model, @RequestParam(required = false) String keyword) {
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }

        List<Inventory> inventory = inventoryService.getAllInventory(keyword);
        model.addAttribute("inventory", inventory);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.delete(id);
        return "redirect:/inventory";
    }
}