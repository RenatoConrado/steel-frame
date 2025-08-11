package io.github.renatoconrado.steel_frame.clients;

import io.github.renatoconrado.steel_frame.clients.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(catalog = "courses", schema = "public", name = "clients")
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 150)
    @NotNull
    @Column(name = "client_id", nullable = false, length = 150)
    private String clientId;

    @Size(max = 400)
    @NotNull
    @Column(name = "client_secret", nullable = false, length = 400)
    private String clientSecret;

    @Size(max = 200)
    @NotNull
    @Column(name = "redirect_uri", nullable = false, length = 200)
    private String redirectUri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    private Role scope;

}
