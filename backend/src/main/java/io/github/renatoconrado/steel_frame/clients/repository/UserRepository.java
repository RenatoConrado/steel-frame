package io.github.renatoconrado.steel_frame.clients.repository;

import io.github.renatoconrado.steel_frame.clients.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository
    extends Repository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> getUserById(UUID id);

    Optional<User> getUserByUsername(String username);
}
