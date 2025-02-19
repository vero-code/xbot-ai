package org.example.xbotai.service;

import org.example.xbotai.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> addUser(UserDto userDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
