package sixman.helfit.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;

import sixman.helfit.domain.board.repository.BoardRepository;
import sixman.helfit.domain.board.repository.BoardTagRepository;
import sixman.helfit.domain.category.service.CategoryService;

import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.service.TagService;

import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.entity.UserPrincipal;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final TagService tagService;
    private final CategoryService categoryService;

    private final UserService userService;
    private final BoardTagRepository boardTagRepository;

    public BoardService(BoardRepository boardRepository, TagService tagService, CategoryService categoryService,
                        UserService userService, BoardTagRepository boardTagRepository) {
        this.boardRepository = boardRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.boardTagRepository = boardTagRepository;
    }

    public Board createBoard(Board board, UserPrincipal userPrincipal){
        verifyBoard(board,userPrincipal);
        for (BoardTag boardTag: board.getBoardTags()) {
            boardTag.addTag(tagService.findTag(boardTag.getTag()));
        }
        return boardRepository.save(board);
    }

    public Page<Board> findBoards(int page) {
        return boardRepository.findAll(PageRequest.of(page,10,
                Sort.by("boardId").descending()));
    }

    public Page<Board> findBoards(Long categoryId, int page) {
        return boardRepository.findBoardByCategoryId(categoryId,PageRequest.of(page,10,
                Sort.by("boardId").descending()));
    }

    public Board updateBoard(Board board,Long categoryId,Long userId, Long boardId, UserPrincipal userPrincipal){
        Board findBoard = findBoardByAllId(categoryId,boardId);
        verifyBoard(findBoard,userPrincipal);


        Optional.ofNullable(board.getTitle())
                .ifPresent(findBoard::setTitle);
        Optional.ofNullable(board.getText())
                .ifPresent(findBoard::setText);
        Optional.ofNullable(board.getBoardImageUrl())
                .ifPresent(findBoard::setBoardImageUrl);
        List<BoardTag> updatedBoardTags = board.getBoardTags();
        if (updatedBoardTags != null) {
            for(BoardTag boardTag : findBoard.getBoardTags()){
                boardTagRepository.delete(boardTag);
            }
            findBoard.getBoardTags().clear();
            for (BoardTag updatedBoardTag : updatedBoardTags) {
                updatedBoardTag.setBoard(findBoard);
                Tag tag = tagService.findTag(updatedBoardTag.getTag());
                updatedBoardTag.addTag(tag);
            }
            board.setBoardTags(updatedBoardTags);
        }
        return boardRepository.save(findBoard);
    }

    private void verifyBoard(Board board,UserPrincipal userPrincipal) {
        User user = userService.findUserByUserId(board.getUser().getUserId());
        if(!Objects.equals(user.getUserId(), userPrincipal.getUser().getUserId())) {
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
        }
        categoryService.verifiedCategoryById(board.getCategory().getCategoryId());
    }

    public Board findBoardById(Long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public Board findBoardByAllId(Long categoryId, Long boardId){
        Optional<Board> optionalBoard = boardRepository.findBoardByIds(categoryId, boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
