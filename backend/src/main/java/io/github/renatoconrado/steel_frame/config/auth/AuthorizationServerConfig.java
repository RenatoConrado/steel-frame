package io.github.renatoconrado.steel_frame.config.auth;

import io.github.renatoconrado.steel_frame.config.auth.user.JwtJpaAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
class AuthorizationServerConfig {

    /**
     * exceptionHandlerConfigurer redirect to the login page when not authenticated
     * from the authorization endpoint.
     */
    @Bean
    @Order(1)
    SecurityFilterChain authorizationSecurityFilterChain(
        HttpSecurity http,
        JwtJpaAuthenticationFilter jwtJpaAuthenticationFilter
    ) throws Exception {
        var authorizationServer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http.securityMatcher(authorizationServer.getEndpointsMatcher());

        http.with(
            authorizationServer,
            serverConfigurer -> serverConfigurer.oidc(Customizer.withDefaults())
        );

        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        http.exceptionHandling(exceptionConfigurer ->
            exceptionConfigurer.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        );

        http.cors(Customizer.withDefaults());

        http.addFilterAfter(
            jwtJpaAuthenticationFilter,
            BearerTokenAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
            .requireAuthorizationConsent(false)
            .requireProofKey(true)
            .build();
    }
}
