package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.tag.entity.Tag;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_TAGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardTag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;



}
