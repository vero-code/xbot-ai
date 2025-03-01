package org.example.xbotai.provider;

import org.example.xbotai.config.SocialMediaBotProperties;
import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.service.ui.SocialAccountBotService;
import org.springframework.stereotype.Component;

@Component
public class SystemSocialMediaBotPropertiesProvider {

    private final SocialAccountBotService socialAccountBotService;

    public SystemSocialMediaBotPropertiesProvider(SocialAccountBotService socialAccountBotService) {
        this.socialAccountBotService = socialAccountBotService;
    }

    public SocialMediaBotProperties getProperties() {
        SocialAccountBotDto dto = socialAccountBotService.getDefaultSocialAccount();
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
