package io.github.renatoconrado.steel_frame.clients.repository;

import io.github.renatoconrado.steel_frame.clients.model.Role;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface RoleRepository extends Repository<Role, UUID> {}
