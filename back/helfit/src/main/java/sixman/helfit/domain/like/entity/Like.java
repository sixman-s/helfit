package sixman.helfit.domain.like.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.user.entity.User;


import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "LIKES")
@NoArgsConstructor
@AllArgsConstructor
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void addBoard(Board board) {
        this.board = board;
        if(!board.getLikes().contains(this)){
            board.getLikes().add(this);
        }
    }

    public Like(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
