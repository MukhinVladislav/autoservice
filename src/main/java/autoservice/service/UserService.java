package autoservice.service;

import autoservice.entity.Client;
import autoservice.entity.Role;
import autoservice.entity.User;
import autoservice.repository.ClientRepository;
import autoservice.repository.RoleRepository;
import autoservice.service.ClientService;
import autoservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String name, String login, String password, String email, String phone, String roleName) {


        if (userRepository.existsByU01Login(login)) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        if (userRepository.existsByU01Email(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }


        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));


        User user = new User();
        user.setU01_NAME(name);
        user.setU01_LOGIN(login);
        user.setU01_PASS(passwordEncoder.encode(password));
        user.setU01_EMAIL(email);
        user.setU01_PHONE(phone);
        user.setRole(role);


        userRepository.save(user);
        System.out.println("Role received: " + role);


        if (role != null && "CLIENT".equals(role.getR01_NAME())) {
            System.out.println("Creating client");

            Client client = new Client();
            client.setName(name);
            client.setPhone(phone);
            client.setEmail(email);


            client.setUser(user);


            Client savedClient = clientRepository.save(client);
            System.out.println("Client saved: " + savedClient);
        }
    }


    public List<User> getAllUsers(String keyword) {
        System.out.println("Вызов getAllLicenses с параметром: " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.search(keyword);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByLogin(username).orElse(null);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUser(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.search(keyword);
    }
}


