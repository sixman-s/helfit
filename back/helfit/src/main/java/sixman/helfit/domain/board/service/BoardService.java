package sixman.helfit.domain.board.service;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;

import sixman.helfit.domain.board.repository.BoardRepository;
import sixman.helfit.domain.board.repository.BoardTagRepository;
import sixman.helfit.domain.category.service.CategoryService;

import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.repository.CommentRepository;
import sixman.helfit.domain.comment.service.CommentService;
import sixman.helfit.domain.like.entity.Like;
import sixman.helfit.domain.like.repository.LikeRepository;
import sixman.helfit.domain.like.service.LikeService;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.service.TagService;

import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.entity.UserPrincipal;

import javax.persistence.EntityManager;
import javax.validation.constraints.Null;
import java.util.*;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final TagService tagService;
    private final CategoryService categoryService;

    private final UserService userService;
    private final BoardTagRepository boardTagRepository;
    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;
    private final LikeService likeService;


    public BoardService(BoardRepository boardRepository, TagService tagService, CategoryService categoryService, UserService userService, BoardTagRepository boardTagRepository,
                        CommentRepository commentRepository, LikeRepository likeRepository, LikeService likeService) {
        this.boardRepository = boardRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.boardTagRepository = boardTagRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.likeService = likeService;
    }

    public Board createBoard(Board board, UserPrincipal userPrincipal){
        verifyBoard(board,userPrincipal);
        for (BoardTag boardTag: board.getBoardTags()) {
            boardTag.addTag(tagService.findTag(boardTag.getTag()));
        }
        Board savedBoard =boardRepository.save(board);
        savedBoard.calculateBoardPoint();
        boardTagRepository.saveAll(board.getBoardTags());
        return boardRepository.save(savedBoard);
    }

//    public void updateBoardProfileImage(Long boardId, String imagePath) {
//        Board verifiedBoard = findBoardById(boardId);
//
//        verifiedBoard.setBoardImageUrl(imagePath);
//
//        boardRepository.save(verifiedBoard);
//    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(int page) {
        return boardRepository.findAll(PageRequest.of(page,10,
                Sort.by("boardId").descending()));
    }
    @Transactional(readOnly = true)
    public Page<Board> findBoards(Long categoryId, int page) {
        return boardRepository.findBoardByCategoryId(categoryId,PageRequest.of(page,10,
                Sort.by("boardId").descending()));
    }
    @Transactional(readOnly = true)
    public Long findBoardsCount(Long categoryId){
        return boardRepository.countByCategoryId(categoryId);
    }


    public Board updateBoard(Board board,Long categoryId, Long boardId, UserPrincipal userPrincipal){
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
                boardTag.removeFromTag();
                boardTag.removeFromBoard();
            }
            boardTagRepository.deleteAll(findBoard.getBoardTags());
            for (BoardTag updatedBoardTag : updatedBoardTags) {
                updatedBoardTag.addBoard(findBoard);
                Tag tag = tagService.findTag(updatedBoardTag.getTag());
                updatedBoardTag.addTag(tag);
                tagService.saveTag(tag);
            }
            boardTagRepository.saveAll(updatedBoardTags);
            findBoard.setBoardTags(updatedBoardTags);
        }
        return boardRepository.save(findBoard);
    }

    public void deleteBoard(Long categoryId, Long boardId, UserPrincipal userPrincipal) {
        Board findBoard = findBoardByAllId(categoryId,boardId);
        verifyBoard(findBoard,userPrincipal);
        List<Comment> comments = commentRepository.findComments(boardId);
        commentRepository.deleteAll(comments);
        boardRepository.delete(findBoard);
    }

    public Like createLike(UserPrincipal userPrincipal, Long boardId){
        Optional<Like> optionalLike = likeRepository.findByIds(userPrincipal.getUser().getUserId(),boardId);
        if(optionalLike.isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_LIKE_BOARD);
        }
        else{
            Board board = findBoardById(boardId);
            User user = userPrincipal.getUser();
            Like like = new Like(board,user);
            like.addInBoard();
            Like savedLike = likeService.saveLike(like);
            board.calculateBoardPoint();
            boardRepository.save(board);
            return savedLike;
        }
    }

    public void deleteLike(UserPrincipal userPrincipal, Long boardId){
        Optional<Like> optionalLike = likeRepository.findByIds(userPrincipal.getUser().getUserId(),boardId);
        if(optionalLike.isPresent()){
            Like like = optionalLike.get();
            User user = like.getUser();
            Board board =like.getBoard();
            if(!Objects.equals(userPrincipal.getUser().getUserId(), like.getUser().getUserId())){
                throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
            }
            like.removeLike();
            likeRepository.delete(like);
            userService.saveUser(user);
            board.calculateBoardPoint();
            boardRepository.save(board);
        }
        else{
            throw new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND);
        }
    }
    @Transactional(readOnly = true)
    public List<Board> findBoardFromLikes(UserPrincipal userPrincipal){
        List<Like> likes = likeRepository.findByUserId(userPrincipal.getUser().getUserId());
        List<Board> boards = new ArrayList<>();
        likes.forEach(like->
                boards.add(like.getBoard())
        );
        return boards;
    }
    @Transactional(readOnly = true)
    public long getBoardLikes(Long boardId){
        Board board = findBoardById(boardId);

        return board.getLikes().size();
    }
    @Transactional(readOnly = true)
    public List<Board> getHotBoards(Long categoryId){
        return  boardRepository.findTop5Boards(categoryId,PageRequest.of(0, 5));
    }
    @Transactional(readOnly = true)
    private void verifyBoard(Board board,UserPrincipal userPrincipal) {
        User user = userService.findUserByUserId(board.getUser().getUserId());
        if(!Objects.equals(user.getUserId(), userPrincipal.getUser().getUserId())) {
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
        }
        categoryService.verifiedCategoryById(board.getCategory().getCategoryId());
    }
    @Transactional(readOnly = true)
    public Board findBoardById(Long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public Board findBoardByAllId(Long categoryId, Long boardId){
        Optional<Board> optionalBoard = boardRepository.findBoardByIds(categoryId, boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public void addView(Long boardId){
        Board findBoard = findBoardById(boardId);
        long view = findBoard.getView();
        findBoard.setView(view+1);
        findBoard.calculateBoardPoint();
        boardRepository.save(findBoard);
    }

}
