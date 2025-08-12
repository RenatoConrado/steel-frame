package io.github.renatoconrado.steel_frame.config.auth.user;

import io.github.renatoconrado.steel_frame.clients.User;
import io.github.renatoconrado.steel_frame.clients.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param authentication the authentication request object.
     * @return A {@link JpaAuthentication}
     * @throws UsernameNotFoundException If the {@link User} was not found
     * @throws BadCredentialsException If the given password don't match with the
     * encrypted password
     */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        User validUser = this.userService
            .getUserByUsername(username)
            .orElseThrow(() -> new BadCredentialsException("User not Found"));

        String validPassword = validUser.getPassword();

        boolean passwordMatch = this.passwordEncoder.matches(rawPassword, validPassword);
        if (!passwordMatch) {
            throw new BadCredentialsException("Invalid Password");
        }

        log.info("Getting Authentication {}", validUser);
        return new JpaAuthentication(validUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
