package io.github.renatoconrado.steel_frame.clients.web.dtos;

import io.github.renatoconrado.steel_frame.clients.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for {@link io.github.renatoconrado.steel_frame.clients.User}
 */
public record UserSafeDto(
    @NotNull UUID id,
    @NotNull(message = "Required Field") Role role,
    @Size(max = 100) @NotBlank String username,
    @NotNull @Size(max = 255) @Email String email,
    @Size(max = 255) String fullName,
    OffsetDateTime createdAt
) implements Serializable {}
