package org.example.xbotai.service.ui.impl;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

import org.example.xbotai.config.ApiUrls;
import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.mapper.SocialAccountBotMapper;
import org.example.xbotai.model.SocialAccountBot;
import org.example.xbotai.model.User;
import org.example.xbotai.repository.SocialAccountBotRepository;
import org.example.xbotai.repository.UserRepository;
import org.example.xbotai.service.ui.SocialAccountBotService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SocialAccountBotServiceImpl implements SocialAccountBotService {

    private final SocialAccountBotRepository repository;
    private final SocialAccountBotMapper mapper;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    @Override
    public SocialAccountBotDto saveSocialAccount(SocialAccountBotDto dto) {
        User currentUser = getCurrentUser();

        Optional<SocialAccountBot> existingOpt = repository.findByUser(currentUser);

        SocialAccountBot accountToSave;
        if (existingOpt.isPresent()) {
            SocialAccountBot existing = existingOpt.get();

            existing.setUsername(dto.getUsername());
            existing.setUserId(dto.getUserId());
            existing.setApiKey(dto.getApiKey());
            existing.setApiSecretKey(dto.getApiSecretKey());
            existing.setJwtToken(dto.getJwtToken());
            existing.setAccessToken(dto.getAccessToken());
            existing.setAccessTokenSecret(dto.getAccessTokenSecret());

            accountToSave = existing;
        } else {
            accountToSave = mapper.toEntity(dto, currentUser);
        }

        SocialAccountBot saved = repository.save(accountToSave);
        return mapper.toDto(saved);
    }

    @Override
    public SocialAccountBotDto getSocialAccountByUserId(Long userId) {
        SocialAccountBot account = repository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Social account not found for userId: " + userId));
        return mapper.toDto(account);
    }

    @Override
    public SocialAccountBotDto getDefaultSocialAccount() {
        return repository.findAll().stream()
                .findFirst()
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("No default social account found"));
    }

    /**
     * Retrieves the X user ID by username via Twitter API.
     * Uses Bearer Token authorization.
     *
     * @param username The X account username (user's or bot's account).
     * @param bearerToken The header containing the App Bearer Token.
     * @return user ID as string.
     * @throws ResponseStatusException if user not found or Twitter API fails.
     */
    @Override
    public String fetchUserIdByUsername(String username, String bearerToken) {
        String url = ApiUrls.X_USER_BY_USERNAME + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return (String) data.get("id");
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "X user not found: " + username);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error calling X API: " + e.getStatusCode());
        }
    }
}
