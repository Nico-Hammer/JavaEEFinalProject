package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);

    Role getOrCreateRole(String roleName);
}