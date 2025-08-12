package io.github.renatoconrado.steel_frame.config.auth.user;

import io.github.renatoconrado.steel_frame.clients.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class JpaAuthentication implements Authentication {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
            new SimpleGrantedAuthority("ROLE_" + this.user.getRole().getName())
        );
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    /**
     * @param isAuthenticated {@code true} if the token should be trusted (which may
     * result in an exception) or {@code false} if the token should not be trusted
     * @throws IllegalArgumentException if an attempt to make the authentication token
     * trusted (by passing {@code true} as the argument) is rejected due to the
     * implementation being immutable or implementing its own alternative approach to
     * {@link #isAuthenticated()}
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) {}

    @Override
    public String getName() {
        return this.user.getUsername();
    }
}
