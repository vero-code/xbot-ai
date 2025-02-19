package org.example.xbotai.service.core.impl;

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
import org.example.xbotai.provider.SocialMediaUserPropertiesProvider;
import org.example.xbotai.service.core.SocialMediaService;
import org.example.xbotai.service.core.TrendService;
import org.example.xbotai.util.SocialMediaCommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocialMediaBotMentionService {

    private final SocialMediaUserPropertiesProvider propertiesProvider;
    private final SocialMediaBotProperties socialMediaBotProperties;
    private final OAuth10aService oAuthService;
    private final OAuth1AccessToken accessToken;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SocialMediaService socialMediaService;
    private final TrendService trendService;
    private final Logger logger = LoggerFactory.getLogger(SocialMediaBotMentionService.class);
    private final TrendsCommandResponder trendsCommandResponder;
    private String lastSeenMentionId = null;

    public SocialMediaBotMentionService(
                                     SocialMediaUserPropertiesProvider propertiesProvider,
                                     @Qualifier("botProperties") SocialMediaBotProperties socialMediaBotProperties,
                                     SocialMediaService socialMediaService,
                                     TrendService trendService,
                                     TrendsCommandResponder trendsCommandResponder) {
        this.propertiesProvider = propertiesProvider;
        this.socialMediaBotProperties = socialMediaBotProperties;
        this.socialMediaService = socialMediaService;
        this.trendService = trendService;
        this.trendsCommandResponder = trendsCommandResponder;
        this.oAuthService = new ServiceBuilder(socialMediaBotProperties.getApiKey())
                .apiSecret(socialMediaBotProperties.getApiSecretKey())
                .build(TwitterApi.instance());
        this.accessToken = new OAuth1AccessToken(
                socialMediaBotProperties.getAccessToken(),
                socialMediaBotProperties.getAccessTokenSecret());
    }

    /**
     * Polls for tweets in the bot's account that mention the bot every 15 minutes.
     * Then filters them to keep only tweets sent from the selected user's account.
     */
    @Scheduled(fixedDelay = 900000)
    public void pollMentions() {
        try {
            String botId = socialMediaBotProperties.getUserID();
            String url = "https://api.twitter.com/2/users/" + botId + "/mentions?tweet.fields=author_id";
            if (lastSeenMentionId != null) {
                url += "&since_id=" + lastSeenMentionId;
            }
            OAuthRequest request = createOAuthRequest(url);
            Response response = oAuthService.execute(request);

            if (response.getCode() == 200) {
                processMentionsResponse(response.getBody(), botId);
            } else {
                logger.error("Error getting mentions: " + response.getCode() + " " + response.getBody());
                fallbackToRecentSearch(botId);
            }
        } catch (Exception e) {
            logger.error("Exception when polling mentions", e);
        }
    }

    /**
     * Fallback method if the main request does not work.
     */
    private void fallbackToRecentSearch(String botId) {
        try {
            String fallbackUrl = "https://api.twitter.com/2/tweets/search/recent?query=%40"
                    + socialMediaBotProperties.getUsername()
                    + "&max_results=10&tweet.fields=author_id";
            OAuthRequest fallbackRequest = createOAuthRequest(fallbackUrl);
            Response fallbackResponse = oAuthService.execute(fallbackRequest);
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
        JsonNode root = objectMapper.readTree(responseBody);
        if (root.has("data")) {
            JsonNode data = root.get("data");
            for (JsonNode tweet : data) {
                processSingleTweet(tweet);
            }
        } else {
            logger.info("No new mentions found.");
        }
    }

    /**
     * Filters tweets, leaving only those authored by a specific user.
     * If the tweet text contains the commands and a mention of a bot, replies to it.
     */
    private void processSingleTweet(JsonNode tweet) {
        String tweetId = tweet.get("id").asText();
        String text = tweet.get("text").asText();

        String tweetAuthorId = tweet.has("author_id") ? tweet.get("author_id").asText() : null;
        if (tweetAuthorId == null) {
            logger.warn("Tweet without author_id, skipping: " + tweet.toString());
            return;
        }

        String userId = propertiesProvider.getPropertiesForCurrentUser().getUserID();

        if (!tweetAuthorId.equals(userId)) {
            logger.info("Skipping tweet not sent by selected user. Tweet author_id: {}", tweetAuthorId);
            return;
        }

        String botMention = "@" + socialMediaBotProperties.getUsername();
        if (text.toLowerCase().contains("trends") && text.contains(botMention)) {
            logger.info("Detected 'trends' command in tweet: {}", text);
            trendsCommandResponder.askCountryForTrends(tweetId, text);
        } else if (text.toLowerCase().contains("country") && text.contains(botMention)){
            logger.info("Detected 'country' command in tweet: {}", text);
            String userCountry = SocialMediaCommandParser.parseAllWordsAfterAsOne(text, "country");
            logger.info("userCountry: {}", userCountry);
            if (userCountry != null) {

                if (!userCountry.equals("canada") && !userCountry.equals("united_states")) {
                    logger.info("Invalid country specified, using default 'united_states'");
                    userCountry = "united_states";
                }
                logger.info("User specified country: {}", userCountry);
            } else {
                logger.info("No word found after 'country', using default 'united_states'");
                userCountry = "united_states";
            }

            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8080/api/bot/trends?country=" + userCountry;
                logger.info("Sending request to URL: {}", url);
                List<String> trends = restTemplate.getForObject(url, List.class);
                logger.info("Trends received: {}", trends);

                trendsCommandResponder.displayTrends(tweetId, trends);
            } catch (Exception e) {
                logger.error("Error calling trends API", e);
            }
        } else if (text.toLowerCase().contains("trend") && text.contains(botMention)) {
            logger.info("Detected 'trend' command in tweet: {}", text);

            String selectedTrend = SocialMediaCommandParser.parseAllWordsAfter(text, "trend");
            logger.info("userTrend: {}", selectedTrend);

            if (selectedTrend == null) {
                logger.info("Invalid trend specified, using default 'Hello world'");
                selectedTrend = "Hello world";
            }

            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8080/api/bot/select-trend";
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("userId", userId);
                requestBody.put("trend", selectedTrend);
                String response = restTemplate.postForObject(url, requestBody, String.class);
                logger.info("Select trend response: {}", response);

                String generateTweetUrl = "http://localhost:8080/api/bot/generate-tweet?userId=" + userId;
                String generatedTweetResponse = restTemplate.getForObject(generateTweetUrl, String.class);
                logger.info("Generated tweet: {}", generatedTweetResponse);

                try {
                    String urlPostTweet = "http://localhost:8080/api/bot/post-tweet";
                    Map<String, String> requestBodyPostTweet = new HashMap<>();
                    requestBodyPostTweet.put("userId", userId);
                    requestBodyPostTweet.put("tweet", generatedTweetResponse);
                    String responsePostTweet = restTemplate.postForObject(urlPostTweet, requestBodyPostTweet, String.class);
                    logger.info("Post tweet response: {}", responsePostTweet);

                    String botAnswer = "The tweet was posted on your behalf. Contact me again!";
                    trendsCommandResponder.displayGeneratedTweet(tweetId, botAnswer);
                } catch (Exception e) {
                    logger.error("Error calling confirm tweet API", e);
                }
            } catch (Exception e) {
                logger.error("Error calling trend selection/generation API", e);
            }
        } else {
            logger.info("Tweet does not contain required command, skipping: {}", text);
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
     * Gets the X user ID by username.
     */
    private String getUserId(String username) throws Exception {
        String url = "https://api.twitter.com/2/users/by/username/" + username;
        OAuthRequest request = createOAuthRequest(url);
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
