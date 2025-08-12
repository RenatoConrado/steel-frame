package io.github.renatoconrado.steel_frame.clients.web;

import io.github.renatoconrado.steel_frame.clients.User;
import io.github.renatoconrado.steel_frame.clients.UserService;
import io.github.renatoconrado.steel_frame.clients.web.dtos.UserDto;
import io.github.renatoconrado.steel_frame.clients.web.dtos.UserMapper;
import io.github.renatoconrado.steel_frame.clients.web.dtos.UserSafeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserSafeDto>> query(
        @RequestParam(required = false) final String username,
        @RequestParam(required = false) final String email,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10", value = "page-size") Integer pageSize
    ) {
        return ResponseEntity.ok(
            this.userService.query(username, email, page, pageSize).map(this.mapper::userToSafeDto)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody UserDto dto) {
        User user = this.mapper.toEntity(dto);
        this.userService.save(user);
    }
}
