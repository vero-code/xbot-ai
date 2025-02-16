package org.example.xbotai.config.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialMediaUserConfig {

    @Value("${x.user.apiKey}")
    private String apiKey;

    @Value("${x.user.apiSecretKey}")
    private String apiSecretKey;

    @Value("${x.user.accessToken}")
    private String accessToken;

    @Value("${x.user.accessTokenSecret}")
    private String accessTokenSecret;

    @Value("${x.user.username}")
    private String username;

    @Bean(name = "userProperties")
    public SocialMediaUserProperties xProperties() {
        SocialMediaUserProperties properties = new SocialMediaUserProperties();
        properties.setApiKey(apiKey);
        properties.setApiSecretKey(apiSecretKey);
        properties.setAccessToken(accessToken);
        properties.setAccessTokenSecret(accessTokenSecret);
        properties.setUsername(username);
        return properties;
    }
}
