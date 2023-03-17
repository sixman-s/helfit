package sixman.helfit.domain.physical.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.entity.QPhysical;
import sixman.helfit.domain.physical.repository.custom.PhysicalRepositoryCustom;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static sixman.helfit.domain.physical.entity.QPhysical.physical;

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
                    userId(userId),
                    withinToday(LocalDateTime.now())
                )
                .fetchFirst()
        );
    }

    @Override
    public List<Physical> findAllPhysicalByUserId(Long userId) {
        return queryFactory.selectFrom(physical)
            .where(userId(userId))
            .orderBy(physical.createdAt.desc())
            .fetch();
    }

    private BooleanExpression userId(Long userId) {
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
