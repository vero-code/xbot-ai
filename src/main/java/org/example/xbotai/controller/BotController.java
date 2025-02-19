package org.example.xbotai.controller;

import org.example.xbotai.dto.TrendSelectionRequest;
import org.example.xbotai.dto.TweetGenerationRequest;
import org.example.xbotai.service.core.AIService;
import org.example.xbotai.service.core.SocialMediaService;
import org.example.xbotai.service.core.TrendService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    private final TrendService trendService;

    private final AIService aiService;

    private final SocialMediaService socialMediaService;

    private final Map<String, String> userGeneratedTweets = new HashMap<>();

    private final Map<String, String> userSelectedTrends = new HashMap<>();

    public BotController(TrendService trendService, AIService aiService, SocialMediaService socialMediaService) {
        this.trendService = trendService;
        this.aiService = aiService;
        this.socialMediaService = socialMediaService;
    }

    @GetMapping("/trends")
    public List<String> getTrends(@RequestParam(defaultValue = "united_states") String country) {
        return trendService.fetchTrends(country);
    }

    @PostMapping("/select-trend")
    public String selectTrend(@RequestBody TrendSelectionRequest request) {
        String userId = request.userId();
        String trend = request.trend();
        userSelectedTrends.put(userId, trend);
        return "Trend '" + trend + "' selected for user " + userId;
    }

    @GetMapping("/generate-tweet")
    public String generateTweet(@RequestParam String userId) {
        String selectedTrend = userSelectedTrends.get(userId);

        if (selectedTrend == null) {
            return "Please select a trend before generating a tweet.";
        }

        String generatedTweet = aiService.generateTweet(selectedTrend);
        userGeneratedTweets.put(userId, generatedTweet);

        return generatedTweet;
    }

    @PostMapping("/confirm-tweet")
    public String confirmTweet(@RequestParam String userId, @RequestParam boolean confirm) {
        String tweetToPost = userGeneratedTweets.get(userId);

        if (tweetToPost == null) {
            return "No tweet generated to confirm. Please generate a tweet first.";
        }

        if (confirm) {
            String response = socialMediaService.postUserTweet(tweetToPost, true);
            userGeneratedTweets.remove(userId);
            return response;
        } else {
            userGeneratedTweets.remove(userId);
            return "Tweet posting canceled.";
        }
    }

    @PostMapping("/post-tweet")
    public String postTweet(@RequestBody TweetGenerationRequest request) {
        String userId = request.userId();
        String tweetToPost = request.tweet();

        if (tweetToPost == null) {
            return "No tweet generated to confirm. Please generate a tweet first.";
        }

        String response = socialMediaService.postUserTweet(tweetToPost, true);
        userGeneratedTweets.remove(userId);
        return response;
    }
}
