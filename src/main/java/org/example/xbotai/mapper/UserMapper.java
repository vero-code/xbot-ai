package org.example.xbotai.mapper;

import org.example.xbotai.dto.UserDto;
import org.example.xbotai.dto.UserProfileDto;
import org.example.xbotai.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
        return userDto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        return user;
    }

    public UserProfileDto toProfileDto(User user) {
        return new UserProfileDto(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
