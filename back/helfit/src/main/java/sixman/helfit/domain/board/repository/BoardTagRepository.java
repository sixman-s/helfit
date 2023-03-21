package sixman.helfit.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.board.entity.BoardTag;

public interface BoardTagRepository  extends JpaRepository<BoardTag, Long> {
}
