package org.example.xbotai.service.impl;

import org.example.xbotai.service.SocialMediaService;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class TrendsCommandResponder {

    private final SocialMediaService socialMediaService;

    private final Logger logger = LoggerFactory.getLogger(TrendsCommandResponder.class);

    public TrendsCommandResponder(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    /**
     * Replies to a tweet that contains the "trends" command.
     * Posts on behalf of the bot.
     */
    public void askCountryForTrends(String tweetId, String text) {
        try {
            String botAnswer = "Enter your country to search for trends (United States, Canada)";
            String postResponse = socialMediaService.postBotReplyTweet(botAnswer, tweetId, false);
            logger.info("Reply posted for tweet ID {}. Response: {}", tweetId, postResponse);
        } catch (Exception e) {
            logger.error("Error processing trends command", e);
        }
    }

    public void displayTrends(String tweetId, List<String> trends) {
        try {
            String trendsString = String.join(",", trends);
            String botAnswer = "Choose trend: " + trendsString;

            int maxLength = 280;
            if (botAnswer.length() > maxLength) {
                botAnswer = botAnswer.substring(0, maxLength);
            }

            String postResponse = socialMediaService.postBotReplyTweet(botAnswer, tweetId, false);
            logger.info("Reply posted for tweet ID {}. Response: {}", tweetId, postResponse);
        } catch (Exception e) {
            logger.error("Error processing trends command", e);
        }
    }
}