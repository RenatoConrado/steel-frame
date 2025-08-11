package io.github.renatoconrado.steel_frame.clients;

import io.github.renatoconrado.steel_frame.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public Optional<Client> getClientById(UUID id) {
        return this.clientRepository.getClientById(id);
    }

    public Optional<Client> getClientByClientId(String id) {
        return this.clientRepository.getClientByClientId(id);
    }
}
