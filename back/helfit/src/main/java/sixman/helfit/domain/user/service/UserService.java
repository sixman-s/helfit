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
import sixman.helfit.utils.CustomBeanUtil;

import java.util.Optional;

import static sixman.helfit.domain.user.entity.User.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomBeanUtil<User> customBeanUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        verifyExistsUserId(user.getId());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmailEmailVerifiedYn(EmailVerified.N);
        user.setRoleType(RoleType.USER);
        user.setProviderType(ProviderType.LOCAL);

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        User verifiedUser = findVerifiedUserByUserId(userId);

        User updatedUser = customBeanUtil.copyNonNullProperties(user, verifiedUser);

        return userRepository.save(updatedUser);
    }

    public void updateUserPassword(Long userId) {

    }

    @Transactional(readOnly = true)
    public User findVerifiedUserByUserId(Long userId) {
        Optional<User> byUserId = userRepository.findByUserId(userId);

        return byUserId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void verifyExistsUserId(String id) {
        userRepository.findById(id).ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.USERS_EXISTS);
        });
    }
}
