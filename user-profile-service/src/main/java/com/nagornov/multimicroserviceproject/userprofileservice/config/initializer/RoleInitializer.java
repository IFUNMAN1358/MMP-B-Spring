package com.nagornov.multimicroserviceproject.userprofileservice.config.initializer;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import com.nagornov.multimicroserviceproject.userprofileservice.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleService roleService;

    @PostConstruct
    public void init() {
        addRoleIfNotExists(1, "ROLE_USER");
        addRoleIfNotExists(2, "ROLE_ADMIN");
        addRoleIfNotExists(3, "ROLE_SUPPORT");
        addRoleIfNotExists(4, "ROLE_MASTER");
    }

    private void addRoleIfNotExists(Integer id, String roleName) {
        Optional<Role> role = roleService.findRoleByName(roleName);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setRoleId(id);
            newRole.setName(roleName);
            roleService.createRole(newRole);
        }
    }
}
