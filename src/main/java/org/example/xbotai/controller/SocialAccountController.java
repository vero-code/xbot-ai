package org.example.xbotai.controller;

import lombok.RequiredArgsConstructor;
import org.example.xbotai.dto.SocialAccountDto;
import org.example.xbotai.service.SocialAccountService;
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
}
