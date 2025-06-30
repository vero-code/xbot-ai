package org.example.xbotai.service.ui.impl;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

import org.example.xbotai.config.ApiUrls;
import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.mapper.SocialAccountMapper;
import org.example.xbotai.model.SocialAccount;
import org.example.xbotai.model.User;
import org.example.xbotai.repository.SocialAccountRepository;
import org.example.xbotai.repository.UserRepository;
import org.example.xbotai.service.ui.SocialAccountService;
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
public class SocialAccountServiceImpl implements SocialAccountService {

    private final SocialAccountRepository repository;
    private final SocialAccountMapper mapper;
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
    public SocialAccountDto saveSocialAccount(SocialAccountDto dto) {
        User currentUser = getCurrentUser();

        Optional<SocialAccount> existingOpt = repository.findByUser(currentUser);

        SocialAccount accountToSave;
        if (existingOpt.isPresent()) {
            SocialAccount existing = existingOpt.get();

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

        SocialAccount saved = repository.save(accountToSave);
        return mapper.toDto(saved);
    }

    @Override
    public SocialAccountDto getSocialAccountByUserId(Long userId) {
        SocialAccount account = repository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Social account not found for userId: " + userId));
        return mapper.toDto(account);
    }

    @Override
    public SocialAccountDto getDefaultSocialAccount() {
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
