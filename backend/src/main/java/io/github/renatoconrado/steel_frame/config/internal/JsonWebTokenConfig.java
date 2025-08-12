package io.github.renatoconrado.steel_frame.config.internal;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
public class JsonWebTokenConfig {

    /**
     * @return {@link KeyPair RSA KeyPair} containing Public and Private RSAKey
     * @throws NoSuchAlgorithmException if fail to get Instance of KeyPair Generator
     */
    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPair keyPair;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);

            keyPair = generator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Failed to get KeyPair generator");
        }
        return keyPair;
    }

    /**
     * @return Json Web Key Source
     * @throws NoSuchAlgorithmException see generateRsaKey()
     */
    @Bean
    JWKSource<SecurityContext> jsonWebTokenSourceGenerator()
        throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey
            .Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    JwtDecoder jsonWebTokenDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
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

}
