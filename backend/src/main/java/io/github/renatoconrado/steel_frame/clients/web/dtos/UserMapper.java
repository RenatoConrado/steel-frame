package io.github.renatoconrado.steel_frame.clients.web.dtos;

import io.github.renatoconrado.steel_frame.clients.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    User toEntity(UserDto userDTO);

    UserDto userToDto(User user);

    UserSafeDto userToSafeDto(User user);
}
