package org.example.xbotai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialMediaConfig {

    @Value("${twitter.apiKey}")
    private String apiKey;

    @Value("${twitter.apiSecretKey}")
    private String apiSecretKey;

    @Value("${twitter.accessToken}")
    private String accessToken;

    @Value("${twitter.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public SocialMediaProperties twitterProperties() {
        SocialMediaProperties properties = new SocialMediaProperties();
        properties.setApiKey(apiKey);
        properties.setApiSecretKey(apiSecretKey);
        properties.setAccessToken(accessToken);
        properties.setAccessTokenSecret(accessTokenSecret);
        return properties;
    }
}
