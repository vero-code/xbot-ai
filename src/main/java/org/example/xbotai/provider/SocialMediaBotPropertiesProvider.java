package org.example.xbotai.provider;

import org.example.xbotai.config.SocialMediaBotProperties;
import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.service.ui.SocialAccountBotService;
import org.example.xbotai.service.ui.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SocialMediaBotPropertiesProvider {

    private final SocialAccountBotService socialAccountBotService;
    private final UserService userService;

    public SocialMediaBotPropertiesProvider(SocialAccountBotService socialAccountBotService,
                                            UserService userService) {
        this.socialAccountBotService = socialAccountBotService;
        this.userService = userService;
    }

    public SocialMediaBotProperties getPropertiesForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        SocialAccountBotDto dto = socialAccountBotService.getSocialAccountByUserId(userId);
        SocialMediaBotProperties props = new SocialMediaBotProperties();
        props.setApiKey(dto.getApiKey());
        props.setApiSecretKey(dto.getApiSecretKey());
        props.setAccessToken(dto.getAccessToken());
        props.setAccessTokenSecret(dto.getAccessTokenSecret());
        props.setUsername(dto.getUsername());
        props.setUserID(dto.getUserId());
        return props;
    }
}
