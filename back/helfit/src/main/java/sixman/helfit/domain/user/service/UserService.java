package sixman.helfit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.withdraw.service.WithdrawService;
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
    private final PasswordEncoder passwordEncoder;
    private final CustomBeanUtil<User> customBeanUtil;

    private final WithdrawService withdrawService;

    private final UserRepository userRepository;

    public User createUser(User user) {
        verifyExistsUserId(user.getId());
        verifyExistsUserEmail(user.getEmail());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmailVerifiedYn(EmailVerified.N);
        user.setRoleType(RoleType.USER);
        user.setProviderType(ProviderType.LOCAL);

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        User verifiedUser = findUserByUserId(userId);

        User updatedUser = customBeanUtil.copyNonNullProperties(user, verifiedUser);

        return userRepository.save(updatedUser);
    }

    public void updateUserEmailVerifiedYn(Long userId) {
        User verifiedUser = findUserByUserId(userId);

        verifiedUser.setEmailVerifiedYn(EmailVerified.Y);

        userRepository.save(verifiedUser);
    }

    public void updateUserPassword(Long userId, User user) {
        User verifiedUser = findUserByUserId(userId);

        if (passwordEncoder.matches(user.getPassword(), verifiedUser.getPassword()))
            throw new BusinessLogicException(ExceptionCode.NOT_CHANGED_PASSWORD);

        verifiedUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(verifiedUser);
    }

    public void withdrawUser(Long userId, User user) {
        User verifiedUser = findUserByUserId(userId);

        if (!passwordEncoder.matches(user.getPassword(), verifiedUser.getPassword()))
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_PASSWORD);

        verifiedUser.setUserStatus(UserStatus.USER_WITHDRAW);

        withdrawService.createWithdraw(verifiedUser);

        userRepository.save(verifiedUser);
    }

    public void updateUserProfileImage(Long userId, String imagePath) {
        User verifiedUser = findUserByUserId(userId);

        verifiedUser.setProfileImageUrl(imagePath);

        userRepository.save(verifiedUser);
    }

    @Transactional(readOnly = true)
    public User findUserByUserId(Long userId) {
        Optional<User> byUserId = userRepository.findByUserId(userId);

        return byUserId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void verifyExistsUserId(String id) {
        userRepository.findById(id).ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXISTS_ID);
        });
    }

    @Transactional(readOnly = true)
    public void verifyExistsUserEmail(String email) {
        userRepository.findByEmail(email).ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXISTS_EMAIL);
        });
    }
}
