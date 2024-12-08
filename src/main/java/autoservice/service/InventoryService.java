package autoservice.service;

import autoservice.entity.Client;
import autoservice.entity.Inventory;
import autoservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> listAll(String keyword) {
        if (keyword != null) {
            return inventoryRepository.search(keyword);
        }
        return inventoryRepository.findAll();
    }

    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }
    public List<Inventory> getAllInventory(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return inventoryRepository.findAll();
        }
        return inventoryRepository.search(keyword);
    }

}
