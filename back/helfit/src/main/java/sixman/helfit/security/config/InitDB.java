package sixman.helfit.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.repository.UserRepository;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import static sixman.helfit.domain.user.entity.User.*;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void initDB() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void dbInit() {
            User user = new User(
                "tester",
                "tester@tester.com",
                "tester",
                null,
                20020101,
                180,
                80,
                EmailVerified.N,
                Gender.MALE,
                ProviderType.LOCAL,
                RoleType.USER
            );
            user.setPassword(passwordEncoder.encode("Test1234!@#$"));

            userRepository.save(user);
        }
    }
}
