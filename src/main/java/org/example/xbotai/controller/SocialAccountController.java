package org.example.xbotai.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.service.ui.SocialAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-account")
@RequiredArgsConstructor
public class SocialAccountController {

    private final SocialAccountService socialAccountService;

    @PostMapping("/save")
    public ResponseEntity<SocialAccountDto> saveSocialAccount(@RequestBody SocialAccountDto dto) {
        SocialAccountDto savedDto = socialAccountService.saveSocialAccount(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SocialAccountDto> getSocialAccount(@PathVariable Long userId) {
        SocialAccountDto dto = socialAccountService.getSocialAccountByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Retrieves the user ID for a given X username.
     * @param username The X account username (user's or bot's account).
     * @param authHeader The authorization header containing the App Bearer Token.
     * @return The map will have a single entry: "userId" mapped to the retrieved user ID string.
     */
    @GetMapping("/get-user-id")
    public ResponseEntity<Map<String, String>> getUserIdByUsername(
        @RequestParam String username,
        @RequestHeader("Authorization") String authHeader) {
        String bearerToken = authHeader.replace("Bearer ", "");
        String userId = socialAccountService.fetchUserIdByUsername(username, bearerToken);
        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
