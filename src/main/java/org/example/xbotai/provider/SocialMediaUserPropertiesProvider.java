package org.example.xbotai.provider;

import org.example.xbotai.config.user.SocialMediaUserProperties;
import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.service.ui.SocialAccountService;
import org.example.xbotai.service.ui.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SocialMediaUserPropertiesProvider {

    private final SocialAccountService socialAccountService;
    private final UserService userService;

    public SocialMediaUserPropertiesProvider(SocialAccountService socialAccountService,
                                             UserService userService) {
        this.socialAccountService = socialAccountService;
        this.userService = userService;
    }

    public SocialMediaUserProperties getPropertiesForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        SocialAccountDto dto = socialAccountService.getSocialAccountByUserId(userId);
        SocialMediaUserProperties props = new SocialMediaUserProperties();
        props.setApiKey(dto.getApiKey());
        props.setApiSecretKey(dto.getApiSecretKey());
        props.setAccessToken(dto.getAccessToken());
        props.setAccessTokenSecret(dto.getAccessTokenSecret());
        props.setUsername(dto.getUsername());
        props.setUserID(dto.getUserId());
        return props;
    }
}
