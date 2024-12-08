package autoservice.service;

import autoservice.entity.Client;
import autoservice.entity.User;
import autoservice.service.OrderService;
import autoservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private OrderService orderService;

    public List<Client> listAll(String keyword) {
        if (keyword != null) {
            return clientRepository.search(keyword);
        }
        return clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    public Optional<Client> findByUser(User user) {
        return clientRepository.findByUser(user);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> getAllClient(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return clientRepository.findAll();
        }
        return clientRepository.search(keyword);
    }

}
