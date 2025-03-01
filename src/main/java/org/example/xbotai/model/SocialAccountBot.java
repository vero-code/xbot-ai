package org.example.xbotai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "social_accounts_bot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccountBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String apiKey;
    private String apiSecretKey;
    private String accessToken;
    private String accessTokenSecret;

    @OneToOne
    @JoinColumn(name = "linked_user_id", unique = true, nullable = false)
    private User user;
}
