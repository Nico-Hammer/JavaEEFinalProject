package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long Id);
    User save(User user);
    void deleteById(Long Id);
}