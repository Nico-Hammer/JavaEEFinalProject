package com.example.FinalProject_CampusJobBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.UserRoles;
import com.example.FinalProject_CampusJobBoard.enums.UserStatus;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String email);
	List<User> findByRole(UserRoles role);
	List<User> findByStatus(UserStatus status);
}
