package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repo;
    public UserServiceImpl(UserRepository repo){this.repo = repo;}

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public User findById(Long Id) {
        return repo.findById(Id).orElse(null);
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public void deleteById(Long Id) {
        repo.deleteById(Id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public List<User> findByStatus(String status) {
        return repo.findByStatus(status);
    }

    @Override
    public List<User> findByRole(String role) {
        return repo.findByRole(role);
    }
}