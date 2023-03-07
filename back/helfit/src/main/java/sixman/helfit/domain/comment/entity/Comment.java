package sixman.helfit.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENTS")
@Getter
@Setter
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false, length = 20000)
    private String commentBody;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
