package autoservice.controller;

import autoservice.entity.Inventory;
import autoservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryRestController {

    private final InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> listAllInventory(@RequestParam(value = "keyword", required = false) String keyword) {
        return inventoryService.listAll(keyword);
    }

    @GetMapping("/{id}")
    public Inventory findInventoryById(@PathVariable Long id) {
        return inventoryService.findById(id).orElseThrow(() -> new RuntimeException("inventory not found"));
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.save(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryService.findById(id);

        if (existingInventory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Inventory updatedInventory = existingInventory.get();
        updatedInventory.setPartName(inventory.getPartName());
        updatedInventory.setCar_name(inventory.getCar_name());
        updatedInventory.setQuantity(inventory.getQuantity());


        inventoryService.save(updatedInventory);
        return ResponseEntity.ok(updatedInventory);
    }

}
