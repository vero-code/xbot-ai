package org.example.xbotai.service.core.impl;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.example.xbotai.config.ApiUrls;
import org.example.xbotai.config.BotCredentialsConfig;
import org.example.xbotai.config.SocialMediaProperties;
import org.example.xbotai.config.SocialMediaUserProperties;
import org.example.xbotai.dto.TweetLogDto;
import org.example.xbotai.model.SocialAccount;
import org.example.xbotai.provider.SystemSocialMediaUserPropertiesProvider;
import org.example.xbotai.repository.SocialAccountRepository;
import org.example.xbotai.service.core.SocialMediaService;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SystemSocialMediaUserPropertiesProvider propertiesProvider;

    private final BotCredentialsConfig botCredentials;

    private final BlockchainService blockchainService;

    private final SocialAccountRepository socialAccountRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    public SocialMediaServiceImpl(SystemSocialMediaUserPropertiesProvider propertiesProvider,
                                  BotCredentialsConfig botCredentials,
                                  BlockchainService blockchainService,
                                  SocialAccountRepository socialAccountRepository) {
        this.propertiesProvider = propertiesProvider;
        this.botCredentials = botCredentials;
        this.blockchainService = blockchainService;
        this.socialAccountRepository = socialAccountRepository;
    }

    /** Post a tweet as a bot. */
    @Override
    public String postBotTweet(String tweetContent, String userId, String selectedTrend, boolean logToBlockchain) {
        return doPostTweet(botCredentials, tweetContent, userId, selectedTrend, logToBlockchain);
    }

    /** Post a tweet as a user. */
    @Override
    public String postUserTweet(String tweetContent, String userId, String selectedTrend, boolean logToBlockchain) {
        // Deciding which account to use to post a tweet
        SocialAccount account = socialAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No social account found for user ID: " + userId));

        SocialMediaUserProperties userProperties = new SocialMediaUserProperties ();
        userProperties.setApiKey(account.getApiKey());
        userProperties.setApiSecretKey(account.getApiSecretKey());
        userProperties.setAccessToken(account.getAccessToken());
        userProperties.setAccessTokenSecret(account.getAccessTokenSecret());
        userProperties.setUsername(account.getUsername());
        userProperties.setUserID(account.getUserId());

        return doPostTweet(userProperties, tweetContent, userId, selectedTrend, logToBlockchain);
    }

    /** General logic for publishing a tweet for any account. */
    private String doPostTweet(SocialMediaProperties props, String tweetContent, String userId, String selectedTrend, boolean logToBlockchain) {
        OAuth10aService service = new ServiceBuilder(props.getApiKey())
                .apiSecret(props.getApiSecret())
                .build(TwitterApi.instance());

        OAuth1AccessToken oauth1AccessToken = new OAuth1AccessToken(
                props.getAccessToken(),
                props.getAccessTokenSecret());

        OAuthRequest request = new OAuthRequest(Verb.POST, ApiUrls.X_TWEETS);
        request.addHeader("Content-Type", "application/json");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("text", tweetContent);
            String payload = objectMapper.writeValueAsString(jsonMap);

            request.setPayload(payload);
            service.signRequest(oauth1AccessToken, request);

            Response response = service.execute(request);
            if (response.getCode() == 201) {
                String body = response.getBody();
                String tweetId = objectMapper.readTree(body).at("/data/id").asText();

                if (logToBlockchain) {
                    TweetLogDto log = createTweetLog(tweetId, userId, props.getUsername(), selectedTrend);

                    ObjectMapper debugMapper = new ObjectMapper();
                    logger.info("Final payload for blockchain: {}", debugMapper.writeValueAsString(log));

                    String blockchainResult = blockchainService.logTweetToBlockchain(log);
                    return "Tweet successfully posted! Blockchain log: " + blockchainResult;
                } else {
                    return "Tweet successfully posted!";
                }
            } else {
                return "Failed to post tweet: " + response.getCode() + " " + response.getBody();
            }
        } catch (Exception e) {
            return "Error occurred while posting tweet: " + e.getMessage();
        }
    }

    @Override
    public String postBotReplyTweet(String tweetContent, String inReplyToTweetId, String userId, boolean logToBlockchain) {
        OAuth10aService service = new ServiceBuilder(botCredentials.getApiKey())
                .apiSecret(botCredentials.getApiSecret())
                .build(TwitterApi.instance());

        OAuth1AccessToken oauth1AccessToken = new OAuth1AccessToken(
                botCredentials.getAccessToken(),
                botCredentials.getAccessTokenSecret());

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> replyMap = new HashMap<>();
            replyMap.put("in_reply_to_tweet_id", inReplyToTweetId);

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("text", tweetContent);
            jsonMap.put("reply", replyMap);

            String payload = objectMapper.writeValueAsString(jsonMap);

            OAuthRequest request = new OAuthRequest(Verb.POST, ApiUrls.X_TWEETS);
            request.addHeader("Content-Type", "application/json");
            request.setPayload(payload);

            service.signRequest(oauth1AccessToken, request);

            Response response = service.execute(request);
            if (response.getCode() == 201) {
                if (logToBlockchain) {
                    String body = response.getBody();
                    String tweetId = objectMapper.readTree(body).at("/data/id").asText();
                    String tweetUrl = "https://x.com/" + botCredentials.getUsername() + "/status/" + tweetId;

                    TweetLogDto log = new TweetLogDto(tweetId, userId, tweetUrl, "(reply)");
                    String blockchainResult = blockchainService.logTweetToBlockchain(log);
                    return "Tweet reply successfully posted! Blockchain log: " + blockchainResult;
                } else {
                    return "Tweet reply successfully posted!";
                }
            } else {
                return "Failed to post tweet reply: " + response.getCode() + " " + response.getBody();
            }
        } catch (Exception e) {
            return "Error occurred while posting tweet reply: " + e.getMessage();
        }
    }

    private TweetLogDto createTweetLog(String tweetId, String userId, String username, String trend) {
        String tweetUrl = "https://x.com/" + username.replace("@", "") + "/status/" + tweetId;
        return new TweetLogDto(tweetId, tweetUrl, userId, trend);
    }
}
