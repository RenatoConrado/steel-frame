package io.github.renatoconrado.steel_frame.clients.repository;

import io.github.renatoconrado.steel_frame.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository
    extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    Optional<Client> getClientById(UUID id);

    Optional<Client> getClientByClientId(String clientId);
}
