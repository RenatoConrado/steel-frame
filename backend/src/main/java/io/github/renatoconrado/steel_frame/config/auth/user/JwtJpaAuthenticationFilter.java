package io.github.renatoconrado.steel_frame.config.auth.user;

import io.github.renatoconrado.steel_frame.clients.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JwtJpaAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var context = SecurityContextHolder.getContext();
        var auth = context.getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtAuthToken) {
            var user = this.userService
                .getUserByUsername(jwtAuthToken.getName())
                .orElseThrow(() -> new BadCredentialsException("Username invalid"));

            var jpaAuth = new JpaAuthentication(user);
            context.setAuthentication(jpaAuth);

        }
        log.info("Auth: {}", auth);

        filterChain.doFilter(request, response);

    }
}
