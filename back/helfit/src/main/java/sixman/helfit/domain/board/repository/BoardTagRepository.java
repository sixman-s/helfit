package sixman.helfit.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;

import java.util.List;

public interface BoardTagRepository  extends JpaRepository<BoardTag, Long> {

    @Query(value = "SELECT b FROM BoardTag b WHERE b.tag.tagName = :tagName")
    Page<BoardTag> findByTag(@Param("tagName") String tagName, PageRequest pageable);

    @Query(value = "SELECT COUNT(*) FROM BoardTag b WHERE b.tag.tagName = :tagName")
    Integer getCountByTag(@Param("tagName") String tagName);
}
