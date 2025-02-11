package org.example.xbotai.controller;

import org.example.xbotai.service.AIService;
import org.example.xbotai.service.TrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    private final TrendService trendService;

    private final AIService aiService;

    public BotController(TrendService trendService, AIService aiService) {
        this.trendService = trendService;
        this.aiService = aiService;
    }

    @GetMapping("/generate-tweet")
    public String generateTweet(@RequestParam(defaultValue = "united_states") String country) {
        String trend = trendService.fetchTrends(country);
        return aiService.generateTweet(trend);
    }
}
