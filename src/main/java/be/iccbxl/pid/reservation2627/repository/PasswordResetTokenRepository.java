package be.iccbxl.pid.reservation2627.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservation2627.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByToken(String token);
}