package org.example.xbotai.controller;

import org.example.xbotai.service.TrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    private final TrendService trendService;

    public BotController(TrendService trendService) {
        this.trendService = trendService;
    }

    @GetMapping("/generate-tweet")
    public String generateTweet() {
        return trendService.fetchTrends();
    }
}
