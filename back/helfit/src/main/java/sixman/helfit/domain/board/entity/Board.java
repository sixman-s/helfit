package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "BOARDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Column(nullable = false, length = 2000)
    private String title;

    @Column(name = "text", length = 20000)
    private String text;

    @Column(length = 512)
    private String boardImageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "board",cascade = CascadeType.PERSIST)
    private Set<BoardLike> boardLikes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @OneToMany(mappedBy = "board",cascade = CascadeType.PERSIST)
    private Set<BoardTag> boardTags = new HashSet<>();

}
