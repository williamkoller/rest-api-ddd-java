package org.example.domain.repositories;

import org.example.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}
