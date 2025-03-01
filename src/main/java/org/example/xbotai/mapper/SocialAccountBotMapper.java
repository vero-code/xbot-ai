package org.example.xbotai.mapper;

import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.model.SocialAccountBot;
import org.example.xbotai.model.User;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountBotMapper {

    public SocialAccountBotDto toDto(SocialAccountBot entity) {
        return new SocialAccountBotDto(
                entity.getId(),
                entity.getUsername(),
                entity.getApiKey(),
                entity.getApiSecretKey(),
                entity.getAccessToken(),
                entity.getAccessTokenSecret()
        );
    }

    public SocialAccountBot toEntity(SocialAccountBotDto dto, User user) {
        SocialAccountBot entity = new SocialAccountBot();
        entity.setUsername(dto.getUsername());
        entity.setApiKey(dto.getApiKey());
        entity.setApiSecretKey(dto.getApiSecretKey());
        entity.setAccessToken(dto.getAccessToken());
        entity.setAccessTokenSecret(dto.getAccessTokenSecret());
        entity.setUser(user);
        return entity;
    }
}
