package org.example.xbotai.service.ui;

import org.example.xbotai.dto.SocialAccountDto;

import java.util.Optional;

public interface SocialAccountService {

    SocialAccountDto saveSocialAccount(SocialAccountDto dto);

    Optional<SocialAccountDto> getSocialAccountByUserId(Long userId);

    SocialAccountDto getDefaultSocialAccount();

    String fetchUserIdByUsername(String username, String bearerToken);
}
