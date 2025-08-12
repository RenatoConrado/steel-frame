package io.github.renatoconrado.steel_frame.config.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Value(
        "${spring.security.oauth2.authorizationserver.client.public-client.registration.redirect-uris}"
    )
    private String redirectUri;

    /**
     * Form login handles the redirect to the login page
     * from the authorization server filter chain.
     */
    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(
        HttpSecurity http
    ) throws Exception {

        http.authorizeHttpRequests(authorize -> {
                authorize.requestMatchers("/public/**", "/users/**").permitAll();
                authorize.anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults())
            .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin(this.redirectUri);
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
