package sixman.helfit.domain.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.board.entity.BoardTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "TAGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    @Column(nullable = false, length = 2000)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<BoardTag> boardTags = new ArrayList<>();

}
