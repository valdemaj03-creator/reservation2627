package be.iccbxl.pid.reservation2627.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservation2627.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCleanup {
    private final PasswordResetTokenRepository tokenRepo;

    @Scheduled(cron = "0 0 * * * *") // toutes les heures
    @Transactional
    public void removeExpiredTokens() {
        tokenRepo.findAll().stream()
                .filter(token -> token.getExpiryDate().isBefore(java.time.LocalDateTime.now()))
                .forEach(token -> tokenRepo.delete(token));
    }
}