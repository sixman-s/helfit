package sixman.helfit.domain.withdraw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.withdraw.entity.Withdraw;
import sixman.helfit.domain.withdraw.repository.WithdrawRepository;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final WithdrawRepository withdrawRepository;

    /*
     * # 탈퇴 회원 정보 생성(암호화 관리:AES256)
     *
     */
    public void createWithdraw(User user) {
        verifyExistWithdraw(user.getId());

        // @Converter 적용
        Withdraw withdraw = Withdraw.builder()
                             .id(user.getId())
                             .email(user.getEmail())
                             .nickname(user.getNickname())
                             .providerType(user.getProviderType())
                             .build();

        withdrawRepository.save(withdraw);
    }

    public void verifyExistWithdraw(String id) {
        Optional<Withdraw> userById = withdrawRepository.findUserById(id);

        userById.ifPresent(e -> {
            throw new BusinessLogicException(ExceptionCode.USER_WITHDRAW);
        });
    }
}
