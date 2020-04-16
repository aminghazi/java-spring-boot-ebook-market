package com.ebookmarket.repository;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordRestToken, Long> {
    PasswordRestToken findByToken(String token);

    PasswordRestToken findByUser(User user);

    Stream<PasswordRestToken> findAllByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from PasswordRestToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
