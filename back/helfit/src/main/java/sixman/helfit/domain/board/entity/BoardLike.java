//package sixman.helfit.domain.board.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import sixman.helfit.domain.like.entity.Like;
//import sixman.helfit.domain.tag.entity.Tag;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "BOARD_LIKES")
//public class BoardLike {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long boardLikeId;
//
//    @ManyToOne
//    @JoinColumn(name = "BOARD_ID")
//    private Board board;
//
//    @ManyToOne
//    @JoinColumn(name = "LIKE_ID")
//    private Like like;
//
//
//
//}
