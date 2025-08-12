package io.github.renatoconrado.steel_frame.clients.web.dtos;

import io.github.renatoconrado.steel_frame.clients.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link io.github.renatoconrado.steel_frame.clients.User}
 */
public record UserDto(
    @NotNull(message = "Required Field")
    Role role,

    @Size(max = 100)
    @NotBlank(message = "Required Field")
    String username,

    @Size(message = "Max Size 255", max = 255)
    @Email(message = "Required to Be Email")
    @NotBlank(message = "Required Field")
    String email,

    @NotNull(message = "Required Field")
    @Size(max = 72)
    String password,

    @Size(max = 255)
    String
    fullName
) implements Serializable {}
