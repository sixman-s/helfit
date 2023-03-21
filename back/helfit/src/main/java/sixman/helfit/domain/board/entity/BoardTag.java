package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.tag.entity.Tag;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BOARD_TAGS")
public class BoardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public void addBoard(Board board) {
        this.board = board;
        this.board.getBoardTags().add(this);
        if(!this.board.getBoardTags().contains(this)){
            this.board.getBoardTags().add(this);
        }
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        this.tag.getBoardTags().add(this);
        if(!this.tag.getBoardTags().contains(this)) {
            this.tag.getBoardTags().add(this);
        }
    }


}
