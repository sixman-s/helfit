package sixman.helfit.domain.physical.repository;

import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.custom.PhysicalRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static sixman.helfit.domain.physical.entity.QPhysical.physical;

@Repository
public class PhysicalRepositoryImpl implements PhysicalRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PhysicalRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Physical> findPhysicalByUserIdWithinToday(Long userId) {
        return Optional.ofNullable(
            queryFactory.selectFrom(physical)
                .where(
                    userIdEq(userId),
                    withinToday(LocalDateTime.now())
                )
                .fetchFirst()
        );
    }

    @Override
    public Page<Physical> findAllPhysicalByUserId(Long userId, Pageable pageable) {
        List<Physical> fetch = queryFactory.selectFrom(physical)
                                   .where(userIdEq(userId))
                                   .orderBy(physical.createdAt.desc())
                                   .offset(pageable.getOffset())
                                   .limit(pageable.getPageSize())
                                   .fetch();

        Long count = queryFactory.select(physical.count())
                         .from(physical)
                         .where(userIdEq(userId))
                         .fetchOne();

        assert count != null;
        return PageableExecutionUtils.getPage(fetch, pageable, count::longValue);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? physical.user.userId.eq(userId) : null;
    }

    private BooleanExpression withinToday(LocalDateTime now) {
        // # Formatter
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        return now != null ? physical.modifiedAt.between(startOfDay, endOfDay.withNano(0)) : null;
    }
}
