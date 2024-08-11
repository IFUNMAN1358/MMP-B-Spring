package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import com.nagornov.multimicroserviceproject.userprofileservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void createRole(Role role) {
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    public Role getRoleByName(String name) {
        try {
            return roleRepository.findRoleByName(name).orElseThrow();
        } catch (Exception e) {
            throw e;
        }
    }

}
