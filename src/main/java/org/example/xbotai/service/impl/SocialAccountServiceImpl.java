package org.example.xbotai.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.mapper.SocialAccountMapper;
import org.example.xbotai.model.SocialAccount;
import org.example.xbotai.model.User;
import org.example.xbotai.repository.SocialAccountRepository;
import org.example.xbotai.repository.UserRepository;
import org.example.xbotai.service.SocialAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialAccountServiceImpl implements SocialAccountService {

    private final SocialAccountRepository repository;
    private final SocialAccountMapper mapper;
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
    public SocialAccountDto saveSocialAccount(SocialAccountDto dto) {
        User currentUser = getCurrentUser();
        SocialAccount entity = mapper.toEntity(dto, currentUser);
        SocialAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }
}
