package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.security.token.PasswordlessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordlessLoginRepository extends JpaRepository<PasswordlessToken, Long> {

    String FIND_UNEXPIRED_PASSWORDLESS_TOKEN_BY_USER = "SELECT * FROM passwordless_token WHERE user_id = :id AND expires_at >= now() AND confirmed_at IS NULL";

    Optional<PasswordlessToken> findPasswordlessTokenByToken(String token);

    @Query(value = FIND_UNEXPIRED_PASSWORDLESS_TOKEN_BY_USER, nativeQuery = true)
    Optional<PasswordlessToken> findUnexpiredPasswordlessTokenByUser(Long id);
}
