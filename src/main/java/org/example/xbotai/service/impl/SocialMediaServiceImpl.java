package org.example.xbotai.service.impl;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.example.xbotai.config.SocialMediaProperties;
import org.example.xbotai.service.SocialMediaService;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaProperties socialMediaProperties;

    private static final String TWEET_ENDPOINT = "https://api.twitter.com/2/tweets";

    public SocialMediaServiceImpl(SocialMediaProperties socialMediaProperties) {
        this.socialMediaProperties = socialMediaProperties;
    }

    @Override
    public String postTweet(String tweetContent) {
        OAuth10aService service = new ServiceBuilder(socialMediaProperties.getApiKey())
                .apiSecret(socialMediaProperties.getApiSecretKey())
                .build(TwitterApi.instance());

        OAuth1AccessToken oauth1AccessToken = new OAuth1AccessToken(socialMediaProperties.getAccessToken(), socialMediaProperties.getAccessTokenSecret());

        OAuthRequest request = new OAuthRequest(Verb.POST, TWEET_ENDPOINT);
        request.addHeader("Content-Type", "application/json");

        String payload = "{\"text\":\"" + tweetContent + "\"}";
        request.setPayload(payload);

        service.signRequest(oauth1AccessToken, request);

        try {
            Response response = service.execute(request);
            if (response.getCode() == 201) {
                return "Tweet successfully posted!";
            } else {
                return "Failed to post tweet: " + response.getCode() + " " + response.getBody();
            }
        } catch (Exception e) {
            return "Error occurred while posting tweet: " + e.getMessage();
        }
    }
}
