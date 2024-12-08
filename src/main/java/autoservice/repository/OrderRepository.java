package autoservice.repository;

import autoservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE CONCAT(o.client.name, ' ', o.status, ' ', o.description,o.car) LIKE %?1%")
    List<Order> search(String keyword);
    List<Order> findAllByClientId(Long client_id);
}
