package io.github.renatoconrado.steel_frame.config.auth.client;

import io.github.renatoconrado.steel_frame.clients.Client;
import io.github.renatoconrado.steel_frame.clients.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    private RegisteredClient mapToRegisteredClient(Client client) {
        return RegisteredClient
            .withId(client.getClientId())
            .clientId(client.getClientId())
            .redirectUri(client.getRedirectUri())
            .scope(client.getScope().getName())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .tokenSettings(this.tokenSettings)
            .clientSettings(this.clientSettings)
            .build();
    }

    /**
     * Not Implemented
     * @param registeredClient the {@link RegisteredClient}
     */
    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = this.clientService
            .getClientById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException("Client Not Found"));

        return this.mapToRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = this.clientService
            .getClientByClientId(clientId)
            .orElseThrow(() -> new EntityNotFoundException("Client Not Found"));

        return this.mapToRegisteredClient(client);
    }
}
