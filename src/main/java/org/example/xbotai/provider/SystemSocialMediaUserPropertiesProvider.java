package org.example.xbotai.provider;

import org.example.xbotai.config.SocialMediaUserProperties;
import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.service.ui.SocialAccountService;
import org.springframework.stereotype.Component;

@Component
public class SystemSocialMediaUserPropertiesProvider {

    private final SocialAccountService socialAccountUserService;

    public SystemSocialMediaUserPropertiesProvider(SocialAccountService socialAccountUserService) {
        this.socialAccountUserService = socialAccountUserService;
    }

    public SocialMediaUserProperties getProperties() {
        SocialAccountDto dto = socialAccountUserService.getDefaultSocialAccount();
        SocialMediaUserProperties props = new SocialMediaUserProperties();
        props.setApiKey(dto.getApiKey());
        props.setApiSecretKey(dto.getApiSecretKey());
        props.setAccessToken(dto.getAccessToken());
        props.setAccessTokenSecret(dto.getAccessTokenSecret());
        props.setUsername(dto.getUsername());
        return props;
    }
}
