package autoservice.repository;

import autoservice.entity.Client;
import autoservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE CONCAT(c.name, ' ', c.phone, ' ', c.email) LIKE %?1%")
    List<Client> search(String keyword);
    Optional<Client> findByUser(User user);
}
