package org.example.xbotai.service.core;

public interface SocialMediaService {

    String postBotTweet(String tweetContent, boolean logToBlockchain);

    String postBotReplyTweet(String tweetContent, String inReplyToTweetId, boolean logToBlockchain);

    String postUserTweet(String tweetContent, boolean logToBlockchain);
}
