package autoservice.controller;

import autoservice.entity.Client;
import autoservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/client")
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> listAllClients(@RequestParam(value = "keyword", required = false) String keyword) {
        return clientService.listAll(keyword);
    }

    @GetMapping("/{id}")
    public Client findClientById(@PathVariable Long id) {
        return clientService.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> existingClient = clientService.findById(id);

        if (existingClient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Client updatedClient = existingClient.get();
        updatedClient.setName(client.getName());
        updatedClient.setPhone(client.getPhone());
        updatedClient.setEmail(client.getEmail());


        clientService.save(updatedClient);
        return ResponseEntity.ok(updatedClient);
    }

}
