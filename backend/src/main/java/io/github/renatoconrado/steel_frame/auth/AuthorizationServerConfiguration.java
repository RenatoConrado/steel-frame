package io.github.renatoconrado.steel_frame.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.time.Duration;

@Configuration
class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    SecurityFilterChain authorizationSecurityFilterChain(
        HttpSecurity http
    ) throws Exception {
        var authorizationServer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http.securityMatcher(authorizationServer.getEndpointsMatcher());

        http.with(
            authorizationServer,
            serverConfigurer -> serverConfigurer.oidc(Customizer.withDefaults())
        );

        http.authorizeHttpRequests(authorize -> {
            authorize.anyRequest().authenticated();
        });

        http.exceptionHandling(exceptionConfigurer ->
            exceptionConfigurer.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        );

        http.cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    TokenSettings tokenSettings() {
        return TokenSettings
            .builder()
            .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//            Access Token: Token das transações 
            .accessTokenTimeToLive(Duration.ofMinutes(60))
//            Refresh Toke: Renova o Access Token
            .refreshTokenTimeToLive(Duration.ofMinutes(90))
            .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder().requireAuthorizationConsent(false).build();
    }
}
