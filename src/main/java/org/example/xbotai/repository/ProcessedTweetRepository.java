package org.example.xbotai.repository;
import org.example.xbotai.model.ProcessedTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedTweetRepository extends JpaRepository<ProcessedTweet, Long> {
  boolean existsByTweetId(String tweetId);
}
