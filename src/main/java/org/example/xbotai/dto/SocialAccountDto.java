package org.example.xbotai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccountDto {

    private Long id;
    private String username;
    private String userId;
    private String apiKey;
    private String apiSecretKey;
    private String jwtToken;
    private String accessToken;
    private String accessTokenSecret;
}
