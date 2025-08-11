package io.github.renatoconrado.steel_frame.auth.internal;

import io.github.renatoconrado.steel_frame.clients.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        return this.userService.getUserByUsername(username).map(user ->
                User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().getName())
                    .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
    }
}
