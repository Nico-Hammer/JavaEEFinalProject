package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User findById(Long Id);

    User save(User user);

    void deleteById(Long Id);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findByStatus(String status);

    List<User> findByRole(String role);
}