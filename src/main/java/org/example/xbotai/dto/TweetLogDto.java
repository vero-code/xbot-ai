package org.example.xbotai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetLogDto {
    private String tweetId;
    private String url;
    private String userId;
    private String trend;
}
