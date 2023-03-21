package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOARDS")
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 2000)
    private String title;

    @Column(name = "text", length = 16383, columnDefinition = "TEXT")
    private String text;

    @Column(length = 512)
    private String boardImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<BoardLike> boardLikes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<BoardTag> boardTags = new ArrayList<>();

    private long view = 0;
}
