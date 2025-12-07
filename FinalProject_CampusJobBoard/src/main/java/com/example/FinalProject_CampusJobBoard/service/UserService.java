package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.UserRoles;
import com.example.FinalProject_CampusJobBoard.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User save(User user);
    void deleteById(Long Id);
}