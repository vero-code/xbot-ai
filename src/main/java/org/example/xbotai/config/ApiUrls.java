package org.example.xbotai.config;

public class ApiUrls {

    // X
    public static final String X_API_BASE = "https://api.twitter.com/2";
    public static final String X_USER_BY_USERNAME = X_API_BASE + "/users/by/username/";
    public static final String X_TWEETS_SEARCH_RECENT = X_API_BASE + "/tweets/search/recent";
    public static final String X_TWEETS = X_API_BASE + "/tweets";

    // Local
    public static final String BACKEND_URL = "http://localhost:8080";

    private ApiUrls() {
    }
  
}
