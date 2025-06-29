package org.example.xbotai.service.ui;

import org.example.xbotai.dto.UserDto;
import org.example.xbotai.model.User;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> addUser(UserDto userDto);

    Long getUserIdByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
