package sixman.helfit.domain.like.entity;

import lombok.Getter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.board.entity.BoardLike;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LIKES")
@Getter
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @OneToMany(mappedBy = "like")
    private Set<BoardLike> boardLikes = new HashSet<>();


}
