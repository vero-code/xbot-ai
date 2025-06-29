package org.example.xbotai.mapper;

import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.model.SocialAccount;
import org.example.xbotai.model.User;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountMapper {

    public SocialAccountDto toDto(SocialAccount entity) {
        return new SocialAccountDto(
                entity.getId(),
                entity.getUsername(),
                entity.getUserId(),
                entity.getApiKey(),
                entity.getApiSecretKey(),
                entity.getJwtToken(),
                entity.getAccessToken(),
                entity.getAccessTokenSecret()
        );
    }

    public SocialAccount toEntity(SocialAccountDto dto, User user) {
        SocialAccount entity = new SocialAccount();
        entity.setUsername(dto.getUsername());
        entity.setUserId(dto.getUserId());
        entity.setApiKey(dto.getApiKey());
        entity.setApiSecretKey(dto.getApiSecretKey());
        entity.setJwtToken(dto.getJwtToken());
        entity.setAccessToken(dto.getAccessToken());
        entity.setAccessTokenSecret(dto.getAccessTokenSecret());
        entity.setUser(user);
        return entity;
    }
}
