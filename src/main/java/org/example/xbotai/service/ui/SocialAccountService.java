package org.example.xbotai.service.ui;

import org.example.xbotai.dto.SocialAccountDto;

public interface SocialAccountService {

    SocialAccountDto saveSocialAccount(SocialAccountDto dto);

    SocialAccountDto getSocialAccountByUserId(Long userId);

    SocialAccountDto getDefaultSocialAccount();
}
