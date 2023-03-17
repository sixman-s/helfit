package sixman.helfit.domain.physical.repository.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import sixman.helfit.domain.physical.entity.Physical;

import java.util.List;

import static sixman.helfit.domain.physical.entity.QPhysical.*;
import static sixman.helfit.domain.user.entity.QUser.*;

@Repository
public class PhysicalTestRepository extends Querydsl5RepositorySupport {

    public PhysicalTestRepository() {
        super(Physical.class);
    }

    public List<Physical> basicSelect() {
        return select(physical)
                   .from(physical)
                   .fetch();
    }

    public List<Physical> basicSelectFrom() {
        return selectFrom(physical)
                   .fetch();
    }

    public Page<Physical> searchByApplyPage(PhysicalTestCond condition, Pageable pageable) {
        JPAQuery<Physical> query =
            selectFrom(physical)
                .leftJoin(physical.user, user)
                .where(userIdEq(condition.getUserId()));

        List<Physical> fetch =
            getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();

        return PageableExecutionUtils.getPage(fetch, pageable, fetch::size);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? physical.user.userId.eq(userId) : null;
    }
}
