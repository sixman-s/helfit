package sixman.helfit.security.mail.repository;

import org.springframework.data.repository.CrudRepository;
import sixman.helfit.security.mail.entity.EmailConfirmRandomKey;

import java.util.Optional;

public interface EmailConfirmRandomKeyRedisRepository extends CrudRepository<EmailConfirmRandomKey, String> {
    Optional<EmailConfirmRandomKey> findByRandomKey(String randomKey);
}
