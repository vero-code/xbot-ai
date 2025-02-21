package org.example.xbotai.service.ui.impl;

import lombok.RequiredArgsConstructor;
import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.mapper.SocialAccountBotMapper;
import org.example.xbotai.model.SocialAccountBot;
import org.example.xbotai.model.User;
import org.example.xbotai.repository.SocialAccountBotRepository;
import org.example.xbotai.repository.UserRepository;
import org.example.xbotai.service.ui.SocialAccountBotService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialAccountBotServiceImpl implements SocialAccountBotService {

    private final SocialAccountBotRepository repository;
    private final SocialAccountBotMapper mapper;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    @Override
    public SocialAccountBotDto saveSocialAccount(SocialAccountBotDto dto) {
        User currentUser = getCurrentUser();
        SocialAccountBot entity = mapper.toEntity(dto, currentUser);
        SocialAccountBot saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public SocialAccountBotDto getSocialAccountByUserId(Long userId) {
        SocialAccountBot account = repository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Social account not found for userId: " + userId));
        return mapper.toDto(account);
    }
}
