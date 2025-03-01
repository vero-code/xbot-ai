package org.example.xbotai.service.core;

public interface AIService {

    String generateTweet(String trend);

    String fetchTrendsFromAI(String country);
}
