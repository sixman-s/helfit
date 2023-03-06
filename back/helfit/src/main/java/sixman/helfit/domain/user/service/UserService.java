package sixman.helfit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.repository.UserRepository;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        verifyExistsUserId(user.getUserId());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setImageUrl("");
        user.setEmailVerifiedYn("N");
        user.setRoleType(RoleType.USER);
        user.setProviderType(ProviderType.LOCAL);

        return userRepository.save(user);
    }

    public User findUser(String userId) {
        return findVerifiedUser(userId);
    }

    @Transactional(readOnly = true)
    public User findVerifiedUser(String userId) {
        Optional<User> optionalMember = userRepository.findByUserId(userId);

        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void verifyExistsUserId(String userId) {
        userRepository.findByUserId(userId).ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.USERS_EXISTS);
        });
    }
}
