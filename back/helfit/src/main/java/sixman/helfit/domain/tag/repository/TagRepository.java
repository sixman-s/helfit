package sixman.helfit.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.tag.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t FROM Tag t WHERE t.tagName = :tagName")
    Optional<Tag> findByTagName(@Param("tagName")String tagName);
}
