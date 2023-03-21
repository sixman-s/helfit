package sixman.helfit.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;
import java.util.*;

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

    @Column(length = 20000)
    private String text;

    @Column(length = 512)
    private String boardImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "board",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
//    private List<BoardLike> boardLikes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    @OneToMany(mappedBy = "board" ,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<BoardTag> boardTags = new ArrayList<>();

    private long view =0;


}
