package com.example.FinalProject_CampusJobBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findByStatus(String status);
    List<User> findByRole(String role);
}