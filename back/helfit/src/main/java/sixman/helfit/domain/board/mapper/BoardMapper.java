package sixman.helfit.domain.board.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.board.dto.BoardDto;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.like.entity.Like;
import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.domain.tag.entity.Tag;

import sixman.helfit.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    default Board boardPostToBoard(BoardDto.Post postDto,Long categoryId, Long userId){
        if ( postDto == null || categoryId == null || userId == null ) {
            return null;
        }
        Board board = new Board();
        User user = new User();
        Category category = new Category();
        user.setUserId(userId);
        category.setCategoryId(categoryId);

        board.setTitle(postDto.getTitle());
        board.setText(postDto.getText());
        board.setBoardImageUrl(postDto.getBoardImageUrl());

        if(postDto.getBoardTags() !=null) {
           List<BoardTag> boardTags = postDto.getBoardTags().stream().
                    map(BoardTag -> {
                        BoardTag boardTag = new BoardTag();
                        Tag tag = new Tag();
                        tag.setTagName(BoardTag);
                        boardTag.addTag(tag);
                        boardTag.addBoard(board);

                        return boardTag;
                    }).collect(Collectors.toList());
            board.setBoardTags(boardTags);
        }
        board.setUser(user);
        board.setCategory(category);

        return board;
    }

    default BoardDto.Response boardToResponse(Board board) {
        if ( board == null ) {
            return null;
        }
        Long boardId = null;
        Long userId = null;
        String userNickname = null;
        String title = null;
        String text = null;
        String boardImageUrl = null;
        List<TagDto.GetResponse> tagNames = new ArrayList<>();
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;
        Long view = null;
        String  userProfileImage = null;
        Integer likesCount =null;
        List<BoardDto.BoardLikeMember> likeUserInfo = new ArrayList<>();
        Long categoryId =null;

        title = board.getTitle();
        text = board.getText();
        boardImageUrl = board.getBoardImageUrl();

        createdAt = board.getCreatedAt();
        modifiedAt = board.getModifiedAt();
        boardId = board.getBoardId();
        userId =board.getUser().getUserId();
        userNickname = board.getUser().getNickname();
        view = board.getView();
        userProfileImage = board.getUser().getProfileImageUrl();
        likesCount = board.getLikes().size();
        categoryId = board.getCategory().getCategoryId();


        List<BoardTag> listBoardTag = board.getBoardTags();
        if (!listBoardTag.isEmpty()) {
            for(BoardTag boardTag : listBoardTag){
                TagDto.GetResponse responseDto = new TagDto.GetResponse(boardTag.getTag().getTagId(),boardTag.getTag().getTagName());
                tagNames.add(responseDto);
            }
        }
        if(!board.getLikes().isEmpty()){
            for(Like like : board.getLikes()){
                BoardDto.BoardLikeMember userInfo = new BoardDto.BoardLikeMember(like.getUser().getUserId(),like.getUser().getProfileImageUrl());
                likeUserInfo.add(userInfo);
            }
        }

        return new BoardDto.Response(boardId,categoryId,userId,view,likesCount,likeUserInfo,userProfileImage,
                userNickname,title,text,boardImageUrl,tagNames,createdAt,modifiedAt);

    }
    List<BoardDto.Response> boardsToResponses(List<Board> boards);

    default Board boardPatchToBoard(BoardDto.Patch patchDto,Long categoryId) {
        if ( patchDto == null || categoryId == null ) {
            return null;
        }

        Board board = new Board();

        board.setTitle( patchDto.getTitle() );
        board.setText( patchDto.getText() );
        board.setBoardImageUrl( patchDto.getBoardImageUrl() );
        if(patchDto.getBoardTags() !=null){
            List<BoardTag> boardTags = patchDto.getBoardTags().stream()
                    .map(boardTagName -> {
                        BoardTag boardTag = new BoardTag();
                        Tag tag = new Tag();
                        tag.setTagName(boardTagName);
                        boardTag.addTag(tag);

                        return boardTag;
                    }).collect(Collectors.toList());
            board.setBoardTags(boardTags);
        }

        return board;
    };
    BoardDto.View boardToBoardView(Board board);
}
