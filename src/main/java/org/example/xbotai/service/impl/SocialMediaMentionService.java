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
import org.example.xbotai.config.bot.SocialMediaBotProperties;
import org.example.xbotai.config.user.SocialMediaUserProperties;
import org.example.xbotai.service.SocialMediaService;
import org.example.xbotai.service.TrendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaMentionService {

    private final SocialMediaUserProperties socialMediaUserProperties;
    private final SocialMediaBotProperties socialMediaBotProperties;
    private final OAuth10aService oAuthService;
    private final OAuth1AccessToken accessToken;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SocialMediaService socialMediaService;
    private final TrendService trendService;
    private final Logger logger = LoggerFactory.getLogger(SocialMediaMentionService.class);
    private String lastSeenMentionId = null;

    public SocialMediaMentionService(@Qualifier("userProperties") SocialMediaUserProperties socialMediaUserProperties,
                                     @Qualifier("botProperties") SocialMediaBotProperties socialMediaBotProperties,
                                     SocialMediaService socialMediaService,
                                     TrendService trendService) {
        this.socialMediaUserProperties = socialMediaUserProperties;
        this.socialMediaBotProperties = socialMediaBotProperties;
        this.socialMediaService = socialMediaService;
        this.trendService = trendService;
        this.oAuthService = new ServiceBuilder(socialMediaUserProperties.getApiKey())
                .apiSecret(socialMediaUserProperties.getApiSecretKey())
                .build(TwitterApi.instance());
        this.accessToken = new OAuth1AccessToken(
                socialMediaUserProperties.getAccessToken(),
                socialMediaUserProperties.getAccessTokenSecret());
    }

    /**
     * Polls for tweets in the user's account that mention the bot every 15 minutes.
     */
    @Scheduled(fixedDelay = 900000)
    public void pollMentions() {
        try {
            String userId = getUserId(socialMediaUserProperties.getUsername());
            String url = "https://api.twitter.com/2/users/" + userId + "/mentions?tweet.fields=author_id";
            if (lastSeenMentionId != null) {
                url += "&since_id=" + lastSeenMentionId;
            }
            OAuthRequest request = createOAuthRequest(url);
            Response response = oAuthService.execute(request);

            if (response.getCode() == 200) {
                processMentionsResponse(response.getBody(), userId);
            } else {
                logger.error("Error getting mentions: " + response.getCode() + " " + response.getBody());
                fallbackToRecentSearch(userId);
            }
        } catch (Exception e) {
            logger.error("Exception when polling mentions", e);
        }
    }

    /**
     * Fallback method using /tweets/search/recent.
     * Here, we search for tweets that mention the bot (using the bot's username).
     */
    private void fallbackToRecentSearch(String botId) {
        try {
            String fallbackUrl = "https://api.twitter.com/2/tweets/search/recent?query=%40"
                    + socialMediaBotProperties.getUsername()
                    + "&max_results=10&tweet.fields=author_id";
            OAuthRequest fallbackRequest = createOAuthRequest(fallbackUrl);
            logger.info("Sending fallback request to URL: " + fallbackUrl);
            Response fallbackResponse = oAuthService.execute(fallbackRequest);
            logger.info("Fallback response code: " + fallbackResponse.getCode());
            logger.info("Fallback response body: " + fallbackResponse.getBody());
            if (fallbackResponse.getCode() == 200) {
                processMentionsResponse(fallbackResponse.getBody(), botId);
            } else {
                logger.error("Fallback endpoint failed with code: "
                        + fallbackResponse.getCode() + " " + fallbackResponse.getBody());
            }
        } catch (Exception e) {
            logger.error("Exception during fallbackToRecentSearch", e);
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
                processSingleTweet(tweet);
            }
        } else {
            logger.info("No new mentions found.");
        }
    }

    /**
     * Processes one tweet with a mention.
     * If a tweet contains a bot mention, respond to it on behalf of the bot.
     */
    private void processSingleTweet(JsonNode tweet) {
        String tweetId = tweet.get("id").asText();
        String text = tweet.get("text").asText();
        logger.info("Tweet ID: " + tweetId + ", text: " + text);

        String tweetAuthorId = tweet.has("author_id") ? tweet.get("author_id").asText() : null;
        if (tweetAuthorId == null) {
            logger.warn("Tweet without author_id, skipping: " + tweet.toString());
            return;
        }
        logger.info("Tweet author_id: " + tweetAuthorId);

        if (tweetAuthorId.equals(getUserIdSilently(socialMediaUserProperties.getUsername()))) {
            logger.info("Skipping tweet sent by user itself, as it is not a command for the bot.");
        }

        String botMention = "@" + socialMediaBotProperties.getUsername();
        if (text.toLowerCase().contains("trends") && text.contains(botMention)) {
            logger.info("Detected 'trends' command in tweet: " + text);
            processTrendsCommand(tweetId, text);
        } else {
            logger.info("Tweet does not contain required command, skipping.");
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
     * Reply to a tweet with a command by sending a response on behalf of the bot.
     */
    private void processTrendsCommand(String tweetId, String text) {
        try {
            logger.info("Replying to tweet with ID: " + tweetId);
            String botAnswer = "Enter your country to search for trends. For example: United States, Canada, etc.";
            String postResponse = socialMediaService.postBotReplyTweet(botAnswer, tweetId, false);
            logger.info("Reply posted for tweet ID " + tweetId + ". Response: " + postResponse);
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

    /**
     * Helper method to get user ID without throwing an exception (returns null on error).
     */
    private String getUserIdSilently(String username) {
        try {
            return getUserId(username);
        } catch (Exception e) {
            logger.error("Error getting user ID silently for username: " + username, e);
            return null;
        }
    }
}