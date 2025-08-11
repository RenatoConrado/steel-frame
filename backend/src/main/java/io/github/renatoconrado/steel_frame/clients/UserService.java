package io.github.renatoconrado.steel_frame.clients;

import io.github.renatoconrado.steel_frame.clients.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.getUserById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }
}
