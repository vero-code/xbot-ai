package org.example.xbotai.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.xbotai.dto.UserDto;
import org.example.xbotai.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
