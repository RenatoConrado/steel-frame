package io.github.renatoconrado.steel_frame.clients;

import io.github.renatoconrado.steel_frame.clients.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.github.renatoconrado.steel_frame.shared.GenericSpecification.likeLower;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.getUserById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    public Page<User> query(
        String username,
        String email,
        Integer page,
        Integer pageSize
    ) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Specification<User> specification = (root, query, cb) -> null;

        specification = specification
            .and(likeLower("username", username))
            .and(likeLower("email", email));

        return this.userRepository.findAll(specification, pageRequest);
    }

    public void save(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }
}
