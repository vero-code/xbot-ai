package org.example.xbotai.service.core.impl;

import org.example.xbotai.service.core.TrendService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${python.api.url}")
    private String pythonApiUrl;

    @Override
    public List<String> fetchTrends(String country) {
        String url = pythonApiUrl + "/trending?country=" + country;
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List<String> trends = response.getBody();

        if (trends != null && !trends.isEmpty()) {
            return trends;
        }
        return Collections.singletonList("No trends available.");
    }
}
