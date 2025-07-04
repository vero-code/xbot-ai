package org.example.xbotai.service.core.impl;

import org.example.xbotai.service.core.AIService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class AIServiceImpl implements AIService {

    @Value("${google.cloud.api.key}")
    private String apiKey;

    @Value("${google.cloud.api.url}")
    private String apiUrl;

    @Override
    public String generateTweet(String trend) {
        RestTemplate restTemplate = new RestTemplate();

        int maxTweetLength = 280;
        String suffix = " (Generated by XBot AI)";
        int maxContentLength = maxTweetLength - suffix.length();

        String prompt = "Create an interesting and relevant tweet on the topic '" + trend +
            "' up to " + maxContentLength + " characters long.";

        JSONObject requestBody = new JSONObject()
                .put("contents", new JSONArray().put(new JSONObject()
                        .put("parts", new JSONArray().put(new JSONObject()
                                .put("text", prompt)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "?key=" + apiKey, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());

            String tweet = jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            if (tweet.length() > maxContentLength) {
                tweet = tweet.substring(0, maxContentLength);
            }

            return tweet + suffix;
        } else {
            return "Error generating tweet: " + response.getStatusCode();
        }
    }

    @Override
    public String fetchTrendsFromAI(String country) {
        RestTemplate restTemplate = new RestTemplate();

        JSONObject requestBody = new JSONObject()
                .put("contents", new JSONArray().put(new JSONObject()
                        .put("parts", new JSONArray().put(new JSONObject()
                                .put("text", "Create 7 hot trending topics that could be popular in the " + country +
                                        " right now. Format them strictly as a comma separated list. Do not include any explanations or extra text.")))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "?key=" + apiKey, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } else {
            return "Error fetching AI-generated trends: " + response.getStatusCode();
        }
    }
}