package org.example.xbotai.service.ui;

import org.example.xbotai.dto.SocialAccountBotDto;

public interface SocialAccountBotService {

    SocialAccountBotDto saveSocialAccount(SocialAccountBotDto dto);

    SocialAccountBotDto getSocialAccountByUserId(Long userId);
}
