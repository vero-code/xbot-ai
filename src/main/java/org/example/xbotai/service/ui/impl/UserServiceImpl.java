package org.example.xbotai.service.ui.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xbotai.dto.UserDto;
import org.example.xbotai.mapper.UserMapper;
import org.example.xbotai.model.User;
import org.example.xbotai.repository.UserRepository;
import org.example.xbotai.service.ui.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDto> addUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            log.error("Registration failed: Username '{}' is already taken!", userDto.getUsername());
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.error("Registration failed: Email '{}' is already in use!", userDto.getEmail());
            throw new IllegalArgumentException("Email is already in use.");
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = userMapper.toEntity(userDto);
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        return Optional.of(userMapper.toDto(savedUser));
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username))
                .getId();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
