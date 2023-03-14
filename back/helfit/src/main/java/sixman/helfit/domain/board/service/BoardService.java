package sixman.helfit.domain.board.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.board.repository.BoardRepository;
import sixman.helfit.domain.category.service.CategoryService;
import sixman.helfit.domain.comment.service.CommentService;
import sixman.helfit.domain.tag.service.TagService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;

import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final TagService tagService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    private final UserService userService;

    public BoardService(BoardRepository boardRepository, TagService tagService, CommentService commentService,
                        CategoryService categoryService, UserService userService) {
        this.boardRepository = boardRepository;
        this.tagService = tagService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Board createBoard(Board board){
        verifyBoard(board);
        for (BoardTag boardTag: board.getBoardTags()) {
            tagService.findTag(boardTag.getTag());
        }
        return boardRepository.save(board);
    }

    private void verifyBoard(Board board) {
        userService.findUserByUserId(board.getUser().getUserId());

        categoryService.verifiedCategoryById(board.getCategory().getCategoryId());
    }


}
