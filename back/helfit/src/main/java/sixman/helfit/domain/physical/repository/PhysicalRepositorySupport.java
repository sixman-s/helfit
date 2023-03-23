package sixman.helfit.domain.physical.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.condition.PhysicalCondition;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.QUser;
import sixman.helfit.support.Querydsl5RepositorySupport;

import java.util.List;

import static sixman.helfit.domain.physical.entity.QPhysical.physical;
import static sixman.helfit.domain.user.entity.QUser.*;

@Repository
public class PhysicalRepositorySupport extends Querydsl5RepositorySupport {

    public PhysicalRepositorySupport() {
        super(Physical.class);
    }

    public Page<Physical> searchWithPageable(PhysicalCondition condition, Pageable pageable) {
        JPAQuery<Physical> query =
            selectFrom(physical)
                .fetchJoin()
                .where(userIdEq(condition.getUserId()));

        List<Physical> content = getQuerydsl().applyPagination(pageable, query).fetch();

        return PageableExecutionUtils.getPage(content, pageable, content::size);
    }

    public Page<Physical> searchWithApply(PhysicalCondition condition, Pageable pageable) {
        return applyPagination(pageable,
            contentQuery -> contentQuery
                                .selectFrom(physical)
                                .where(userIdEq(condition.getUserId())),
            countQuery -> countQuery
                              .select(physical.count())
                              .from(physical)
                              .where(userIdEq(condition.getUserId()))
        );
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? physical.user.userId.eq(userId) : null;
    }
}
