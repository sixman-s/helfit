package sixman.helfit.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
