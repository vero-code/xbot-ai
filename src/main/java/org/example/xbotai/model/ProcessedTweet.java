package org.example.xbotai.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "PROCESSED_TWEETS")
public class ProcessedTweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tweet_id", nullable = false, unique = true)
    private String tweetId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt = LocalDateTime.now();

    public ProcessedTweet() {
    }

    public ProcessedTweet(String tweetId, String userId) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.processedAt = LocalDateTime.now();
    }
}