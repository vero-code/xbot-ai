package org.example.xbotai.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.xbotai.dto.UserDto;
import org.example.xbotai.dto.UserProfileDto;
import org.example.xbotai.mapper.UserMapper;
import org.example.xbotai.model.User;
import org.example.xbotai.service.ui.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        return ResponseEntity.ok(userMapper.toProfileDto(user));
    }
}
