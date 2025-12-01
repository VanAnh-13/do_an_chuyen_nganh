package com.example.talentbridge.repository;

import com.example.talentbridge.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    List<PasswordResetToken> findByUserIdAndUsedFalse(Long userId);

    @Modifying
    @Query("DELETE FROM PasswordResetToken p WHERE p.expiryDate < :date")
    void deleteByExpiryDateBefore(@Param("date") Instant date);

    @Modifying
    @Query("UPDATE PasswordResetToken p SET p.used = true WHERE p.user.id = :userId AND p.used = false")
    void invalidateAllTokensForUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(p) FROM PasswordResetToken p WHERE p.user.email = :email AND p.createdAt > :since")
    long countRecentRequestsByEmail(@Param("email") String email, @Param("since") Instant since);
}
