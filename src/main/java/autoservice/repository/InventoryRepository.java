package autoservice.repository;

import autoservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM Inventory i WHERE CONCAT(i.partName, ' ', i.car_name, ' ', i.quantity) LIKE %?1%")
    List<Inventory> search(String keyword);
}
