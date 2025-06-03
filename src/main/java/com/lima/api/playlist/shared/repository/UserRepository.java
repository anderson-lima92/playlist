package com.lima.api.playlist.shared.repository;

import com.lima.api.playlist.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}