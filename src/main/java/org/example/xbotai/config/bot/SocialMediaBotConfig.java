package org.example.xbotai.config.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialMediaBotConfig {

    @Value("${x.bot.apiKey}")
    private String apiKey;

    @Value("${x.bot.apiSecretKey}")
    private String apiSecretKey;

    @Value("${x.bot.accessToken}")
    private String accessToken;

    @Value("${x.bot.accessTokenSecret}")
    private String accessTokenSecret;

    @Value("${x.bot.username}")
    private String username;

    @Value("${x.bot.userId}")
    private String userID;

    @Bean(name = "botProperties")
    public SocialMediaBotProperties xProperties() {
        SocialMediaBotProperties properties = new SocialMediaBotProperties();
        properties.setApiKey(apiKey);
        properties.setApiSecretKey(apiSecretKey);
        properties.setAccessToken(accessToken);
        properties.setAccessTokenSecret(accessTokenSecret);
        properties.setUsername(username);
        properties.setUserID(userID);
        return properties;
    }
}
