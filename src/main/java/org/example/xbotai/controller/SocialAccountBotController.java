package org.example.xbotai.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.example.xbotai.dto.SocialAccountBotDto;
import org.example.xbotai.service.ui.SocialAccountBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-account-bot")
@RequiredArgsConstructor
public class SocialAccountBotController {

    private final SocialAccountBotService socialAccountBotService;

    @PostMapping("/save")
    public ResponseEntity<SocialAccountBotDto> saveSocialAccount(@RequestBody SocialAccountBotDto dto) {
        SocialAccountBotDto savedDto = socialAccountBotService.saveSocialAccount(dto);
        System.out.println("✅ SocialAccount saved: username=" + savedDto.getUsername());
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SocialAccountBotDto> getSocialAccount(@PathVariable Long userId) {
        SocialAccountBotDto dto = socialAccountBotService.getSocialAccountByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get-user-id")
    public ResponseEntity<Map<String, String>> getUserIdByUsername(
        @RequestParam String username,
        @RequestHeader("X-Token") String authHeader) {
        String bearerToken = authHeader.replace("Bearer ", "");
        String userId = socialAccountBotService.fetchUserIdByUsername(username, bearerToken);
        System.out.println("✅ Retrieved userId from X API: " + userId);
        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
