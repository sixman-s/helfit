package sixman.helfit.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
