package org.example.xbotai.service.impl;

import org.example.xbotai.service.AIService;
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

        JSONObject requestBody = new JSONObject()
                .put("contents", new JSONArray().put(new JSONObject()
                        .put("parts", new JSONArray().put(new JSONObject()
                                .put("text", "Create an interesting and relevant tweet on the topic '" + trend + "' up to 200 characters long.")))));

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
            return "Error generating tweet: " + response.getStatusCode();
        }
    }
}