package autoservice.service;

import autoservice.entity.Role;
import autoservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return roleRepository.findAll();
        }
        return roleRepository.search(keyword);
    }

    public void initializeRoles() {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("WORKER");
        createRoleIfNotExists("CLIENT");
    }

    public void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setR01_NAME(roleName);
            return roleRepository.save(role);
        });
    }
}