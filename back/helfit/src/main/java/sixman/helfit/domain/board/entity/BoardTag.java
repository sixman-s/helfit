package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.tag.entity.Tag;

import javax.persistence.*;

@Entity(name = "BOARD_TAGS")
@NoArgsConstructor
@Getter
@Setter
public class BoardTag{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public void addBoard(Board board){
        this.board = board;
        this.board.getBoardTags().add(this);
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        this.tag.getBoardTags().add(this);
    }


}
