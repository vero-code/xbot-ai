package org.example.xbotai.controller;

import org.example.xbotai.dto.TrendSelectionRequest;
import org.example.xbotai.service.AIService;
import org.example.xbotai.service.SocialMediaService;
import org.example.xbotai.service.TrendService;
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

        return "Generated tweet: \"" + generatedTweet + "\". Please confirm to post.";
    }

    @PostMapping("/confirm-tweet")
    public String confirmTweet(@RequestParam String userId, @RequestParam boolean confirm) {
        String tweetToPost = userGeneratedTweets.get(userId);

        if (tweetToPost == null) {
            return "No tweet generated to confirm. Please generate a tweet first.";
        }

        if (confirm) {
            String response = socialMediaService.postTweet(tweetToPost);
            userGeneratedTweets.remove(userId);
            return response;
        } else {
            userGeneratedTweets.remove(userId);
            return "Tweet posting canceled.";
        }
    }
}
