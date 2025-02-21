package org.example.xbotai.repository;

import org.example.xbotai.model.SocialAccountBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialAccountBotRepository extends JpaRepository<SocialAccountBot, Long> {

    Optional<SocialAccountBot> findByUser_Id(Long userId);
}
