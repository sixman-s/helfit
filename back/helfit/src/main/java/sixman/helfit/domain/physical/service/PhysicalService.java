package sixman.helfit.domain.physical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.PhysicalRepository;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.utils.CustomBeanUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhysicalService {
    private final PhysicalRepository physicalRepository;
    private final CustomBeanUtil<Physical> customBeanUtil;

    public Physical createPhysical(Physical physical, User user) {
        try {
            verifyExistPhysical(user.getUserId());
            physical.setUser(user);

            return physicalRepository.save(physical);
        } catch (BusinessLogicException e) {
            return updatePhysical(physical, user.getUserId());
        }
    }

    public Physical updatePhysical(Physical physical, Long userId) {
        Physical verifiedPhysical = findPhysicalByUserIdWithinToday(userId);

        Physical updatedPhysical = customBeanUtil.copyNonNullProperties(physical, verifiedPhysical);

        return physicalRepository.save(updatedPhysical);
    }

    @Transactional(readOnly = true)
    public Physical findPhysicalByUserIdWithinToday(Long userId) {
        Optional<Physical> physicalByUserIdWithOrderBy = physicalRepository.findPhysicalByUserIdWithinToday(userId);

        return physicalByUserIdWithOrderBy.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Physical findAllPhysicalByUserId(Long userId) {
        Optional<Physical> physicalByUserIdWithOrderBy = physicalRepository.findPhysicalByUserIdWithinToday(userId);

        return physicalByUserIdWithOrderBy.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void verifyExistPhysical(Long userId) {
        Optional<Physical> physicalByUserIdWithOrderBy = physicalRepository.findPhysicalByUserIdWithinToday(userId);

        physicalByUserIdWithOrderBy.ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXISTS_INFORMATION);
        });

    }
}
