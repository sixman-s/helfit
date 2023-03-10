package sixman.helfit.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.tag.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);
}
