package sixman.helfit.security.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.security.mail.entity.EmailConfirmToken;

import java.util.Optional;


public interface EmailConfirmTokenRepository extends JpaRepository<EmailConfirmToken, String> {
    Optional<EmailConfirmToken> findEmailConfirmTokenByTokenId(String tokenId);
}
