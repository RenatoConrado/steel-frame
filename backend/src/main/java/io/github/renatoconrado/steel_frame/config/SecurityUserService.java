package io.github.renatoconrado.steel_frame.config;

import io.github.renatoconrado.steel_frame.clients.User;
import io.github.renatoconrado.steel_frame.clients.UserService;
import io.github.renatoconrado.steel_frame.config.auth.user.JpaAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityUserService {

    private final UserService userService;

    public Optional<User> getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JpaAuthentication jpaAuth) {
            return Optional.of(jpaAuth.getUser());
        }
        return Optional.empty();
    }
}
