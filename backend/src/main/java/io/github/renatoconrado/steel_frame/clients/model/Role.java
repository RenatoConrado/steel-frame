package io.github.renatoconrado.steel_frame.clients.model;

import io.github.renatoconrado.steel_frame.clients.Client;
import io.github.renatoconrado.steel_frame.clients.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(catalog = "courses", schema = "public", name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "scope")
    private Set<Client> clients = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "role_id")
    private Set<User> users = new LinkedHashSet<>();

}
