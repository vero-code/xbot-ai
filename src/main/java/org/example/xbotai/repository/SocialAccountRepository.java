package org.example.xbotai.repository;

import org.example.xbotai.model.SocialAccount;
import org.example.xbotai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    // Public user ID on a social network
    Optional<SocialAccount> findByUser_Id(Long userId);

    Optional<SocialAccount> findByUser(User user);

    // ID from the app DB
    Optional<SocialAccount> findByUserId(String userId);
}
