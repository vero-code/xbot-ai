package org.example.xbotai.controller;

import lombok.RequiredArgsConstructor;
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
}
