package org.example.xbotai.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.example.xbotai.config.SocialMediaProperties;
import org.example.xbotai.service.SocialMediaService;
import org.example.xbotai.service.TrendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaMentionService {

    private final SocialMediaProperties socialMediaProperties;
    private final OAuth10aService oAuthService;
    private final OAuth1AccessToken accessToken;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SocialMediaService socialMediaService;
    private final TrendService trendService;
    private final Logger logger = LoggerFactory.getLogger(SocialMediaMentionService.class);
    private String lastSeenMentionId = null;

    public SocialMediaMentionService(SocialMediaProperties socialMediaProperties,
                                     SocialMediaService socialMediaService,
                                     TrendService trendService) {
        this.socialMediaProperties = socialMediaProperties;
        this.socialMediaService = socialMediaService;
        this.trendService = trendService;
        this.oAuthService = new ServiceBuilder(socialMediaProperties.getApiKey())
                .apiSecret(socialMediaProperties.getApiSecretKey())
                .build(TwitterApi.instance());
        this.accessToken = new OAuth1AccessToken(
                socialMediaProperties.getAccessToken(),
                socialMediaProperties.getAccessTokenSecret());
    }

    /**
     * The method polls for mentions every 15 minutes.
     */
    @Scheduled(fixedDelay = 900000)
    public void pollMentions() {
        try {
            String botId = getUserId(socialMediaProperties.getBotUsername());
            String url = "https://api.twitter.com/2/users/" + botId + "/mentions";
            if (lastSeenMentionId != null) {
                url += "?since_id=" + lastSeenMentionId;
            }
            OAuthRequest request = createOAuthRequest(url);
            Response response = oAuthService.execute(request);

            if (response.getCode() == 200) {
                processMentionsResponse(response.getBody(), botId);
            } else {
                logger.error("Error getting mentions: " + response.getCode() + " " + response.getBody());
            }
        } catch (Exception e) {
            logger.error("Exception when polling mentions", e);
        }
    }

    /**
     * Creates an OAuthRequest with basic settings.
     */
    private OAuthRequest createOAuthRequest(String url) {
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        request.addHeader("Content-Type", "application/json");
        oAuthService.signRequest(accessToken, request);
        return request;
    }

    /**
     * Processes a JSON response with mentions.
     */
    private void processMentionsResponse(String responseBody, String botId) throws Exception {
        logger.info("Processing mentions response: " + responseBody);
        JsonNode root = objectMapper.readTree(responseBody);
        if (root.has("data")) {
            JsonNode data = root.get("data");
            logger.info("Found " + data.size() + " mention(s) in response.");
            for (JsonNode tweet : data) {
                logger.info("Processing tweet JSON: " + tweet.toString());
                processSingleTweet(tweet, botId);
            }
        } else {
            logger.info("No new mentions found.");
        }
    }

    /**
     * Processes one tweet with a mention.
     */
    private void processSingleTweet(JsonNode tweet, String botId) {
        String tweetId = tweet.get("id").asText();
        String text = tweet.get("text").asText();
        logger.info("Tweet ID: " + tweetId + ", text: " + text);

        String tweetAuthorId = tweet.has("author_id") ? tweet.get("author_id").asText() : null;
        if (tweetAuthorId == null) {
            logger.warn("Tweet without author_id, skipping: " + tweet.toString());
            return;
        }
        logger.info("Tweet author_id: " + tweetAuthorId);

        if (!tweetAuthorId.equals(botId)) {
            logger.info("Skipping tweet from another user (author_id: " + tweetAuthorId + "), expected bot ID: " + botId);
            return;
        }

        logger.info("Mention received: " + text);
        if (text.toLowerCase().contains("trends")) {
            logger.info("'trends' command detected. Command Processing for tweet ID: " + tweetId);
            processTrendsCommand(tweetId, text);
        }
        updateLastSeenMentionId(tweetId);
    }

    /**
     * Updates the lastSeenMentionId value if a tweet with a higher ID is found.
     */
    private void updateLastSeenMentionId(String tweetId) {
        if (lastSeenMentionId == null || Long.parseLong(tweetId) > Long.parseLong(lastSeenMentionId)) {
            lastSeenMentionId = tweetId;
        }
    }

    /**
     * Processing the trends command.
     */
    private void processTrendsCommand(String tweetId, String text) {
        try {
            String botAnswer = "Enter your country to search for trends. For example: United States, Canada, etc.";
            String postResponse = socialMediaService.postTweet(botAnswer, false);
            logger.info("Reply when posting a trending tweet: " + postResponse);
        } catch (Exception e) {
            logger.error("Error processing trends command", e);
        }
    }

    /**
     * Gets the X user ID by username.
     */
    private String getUserId(String username) throws Exception {
        String url = "https://api.twitter.com/2/users/by/username/" + username;
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        request.addHeader("Content-Type", "application/json");
        oAuthService.signRequest(accessToken, request);

        Response response = oAuthService.execute(request);
        if (response.getCode() == 200) {
            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.has("data")) {
                return root.get("data").get("id").asText();
            } else {
                throw new Exception("User not found in response.");
            }
        } else {
            throw new Exception("Error getting user ID: " +
                    response.getCode() + " " + response.getBody());
        }
    }
}