import style from '../../styles/Community/P_Detail.module.css';
import React, { useState, useEffect } from 'react';
import UserNav from './C_Community/UserNav';
import axios from 'axios';
import { useRouter } from 'next/router';
import Btn from '../loginc/Buttons';
import Modal from './C_Community/DeletePost';

interface BoardData {
  boardId: number;
  title: string;
  text: string;
  boardImageUrl: string | null;
  tags: {
    tagId: number;
    tagName: string;
  }[];
  createdAt: string;
  modifiedAt: string;
  userId: number;
  userNickname: string;
  userProfileImage: any;
  likeUserInfo: {
    userId: number;
    userProfileImgUrl: string;
  }[];
}
interface UserInfo {
  userId: number;
}

const DetailP = () => {
  const URL = process.env.NEXT_PUBLIC_URL;
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [writeCommnet, setWriteCommnet] = useState('');
  const [comments, setComments] = useState([]);
  const [fetchedData, setFetchedData] = useState<BoardData | null>(null);
  const [viewCount, setViewCount] = useState(0);
  const [likeCount, setLikeCount] = useState(0);
  const [isLiked, setIsLiked] = useState(false);
  const [likeUser, setLikeUser] = useState([]);

  const escapeMap = {
    '&lt;': '<',
    '&#12296;': '<',
    '&gt;': '>',
    '&#12297;': '>',
    '&amp;': '&',
    '&quot;': '"',
    '&#x27;': "'"
  };
  const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;
  const convertToHtml = (text) =>
    text.replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match);

  const router = useRouter();
  const { id } = router.query;
  let boardID: number | null = null;
  if (typeof id === 'string') {
    boardID = parseInt(id.split('/').pop() as string);
  }
  const currentPage = router.asPath.split('/')[2];
  let categoryname: string;
  let pageNumber: Number;
  switch (currentPage) {
    case 'health':
      pageNumber = 1;
      categoryname = '헬스 갤러리';
      break;
    case 'crossfit':
      pageNumber = 2;
      categoryname = '크로스핏 갤러리';
      break;
    case 'pilates':
      pageNumber = 4;
      categoryname = '필라테스 갤러리';
      break;
    case 'oww':
      pageNumber = 5;
      categoryname = '오운완 갤러리';
      break;
    case 'diet':
      pageNumber = 6;
      categoryname = '식단 갤러리';
      break;
    default:
      pageNumber = null;
  }

  // 상세페이지 글이랑 댓글 불러오기
  useEffect(() => {
    const localUserId = JSON.parse(localStorage.UserInfo?.userId ?? 'null');
    axios
      .get(`${URL}/api/v1/board/${pageNumber}/${boardID}`)
      .then((res) => {
        const data = res.data;
        res.data.text = convertToHtml(res.data.text);
        setFetchedData(data);
        setLikeUser(
          data?.likeUserInfo
            ?.map((userInfo) => userInfo.userId)
            .filter((id) => typeof id === 'number') || []
        );
        const isLikedByUser = data?.likeUserInfo
          ?.map((userInfo) => userInfo.userId)
          .includes(Number(localUserId));
        setIsLiked(isLikedByUser);
      })
      .then(() => {
        axios
          .get(`${URL}/api/v1/board/view/${boardID}`)
          .then((res) => setViewCount(res.data.view));
      })
      .then(() => {
        axios
          .get(`${URL}/api/v1/board/likes/${boardID}`)
          .then((res) => setLikeCount(res.data));
      })
      .then(() => {
        axios
          .get(`${URL}/api/v1/comment/${boardID}`)
          .then((res) => setComments(res.data))
          .catch((err) => console.log(err));
      })
      .catch((err) => console.log(err));
  }, [boardID]);

  // 댓글 작성
  const handleSubmit = (e: React.KeyboardEvent) => {
    if (typeof localStorage.accessToken !== 'undefined') {
      const userInfo: UserInfo = JSON.parse(localStorage.UserInfo);
      axios
        .post(
          `${URL}/api/v1/comment/${userInfo.userId}/${fetchedData.boardId}`,
          {
            commentBody: writeCommnet
          }
        )
        .then(() => {
          axios
            .get(`${URL}/api/v1/comment/${boardID}`)
            .then((res) => setComments(res.data))
            .catch((err) => console.log(err));
        })
        .then((res) => console.log(res));

      setWriteCommnet('');
    } else {
      alert('로그인 한 유저만 댓글을 작성할 수 있습니다.');
    }
  };

  // 댓글 삭제 commentId
  const handleDeleteComment = (commentId) => {
    const userInfo: UserInfo = JSON.parse(localStorage.UserInfo);
    axios
      .delete(
        `${URL}/api/v1/comment/${userInfo.userId}/${boardID}/${commentId}`
      )
      .then(() => {
        axios
          .get(`${URL}/api/v1/comment/${boardID}`)
          .then((res) => setComments(res.data))
          .catch((err) => console.log(err));
      })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };

  // 게시글 삭제
  const handleDeletePost = () => {
    const accessToken = localStorage.accessToken;
    axios
      .delete(`${URL}/api/v1/board/${pageNumber}/${boardID}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
      .then(() => {
        alert('성공적으로 삭제되었습니다.');
        router.push(`/community/${currentPage}`);
      })
      .catch((err) => {
        alert(err);
      });
  };

  // 좋아요
  const handleLikeClick = () => {
    const accessToken = localStorage.accessToken;
    axios
      .post(
        `${URL}/api/v1/board/likes/${boardID}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        }
      )
      .then((res) => setLikeCount(res.data))
      .then(() => setIsLiked(!isLiked))
      .then(() => router.reload())
      .catch((err) => {
        if (err.response.status === 403) {
          alert('로그인한 유저만 좋아요를 누를 수 있습니다.');
        } else if (err.response.status === 400) {
          axios
            .delete(`${URL}/api/v1/board/likes/${boardID}`, {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            })
            //.then((res) => console.log(res))
            .then((res) => setLikeCount(res.data))
            .then(() => router.reload())
            .then(() => setIsLiked(!isLiked))
            .catch((err) => alert(err));
        } else {
          alert(err);
        }
      });
  };

  const handlePatch = () => {
    router.push(`/community/patchpost/${currentPage}/${boardID}`);
  };
  const handleDeletePostClick = () => {
    setIsModalOpen(true);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleSubmit(e);
    }
  };

  const pressEnter: React.KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.nativeEvent.isComposing) {
      return;
    }

    if (e.key === 'Enter' && e.shiftKey) {
      return;
    } else if (e.key === 'Enter') {
      e.preventDefault();
      handleSubmit(e);
    }
  };
  const handleClick = (e: React.MouseEvent<HTMLImageElement>) => {
    const keyboardEvent = e as unknown as React.KeyboardEvent;
    handleSubmit(keyboardEvent);
  };

  const createdAtString = new Date(fetchedData?.createdAt)
    .toLocaleDateString('en-KR', {
      year: '2-digit',
      month: '2-digit',
      day: '2-digit'
    })
    .split('/')
    .join('.');

  return (
    <>
      <div className={style.allInput}>
        <div className={style.UserProfile}>
          <UserNav />
        </div>
        <div className={style.Category}>{categoryname}</div>
        <div className={style.Title}>{fetchedData?.title}</div>
        <div className={style.Nav}>
          <div className={style.PostNav}>
            <div className={style.postUser}>
              {fetchedData?.userProfileImage ? (
                <img
                  src={fetchedData?.userProfileImage}
                  alt='user profile'
                  className={style.postUserImg}
                />
              ) : (
                <img
                  src={'../../../assets/Community/UserProfile.svg'}
                  className={style.postUserImg}
                />
              )}
              <div>{fetchedData?.userNickname}</div>
            </div>
            <div>{createdAtString}</div>
            <div className={style.view}>
              <div>조회수 : </div>
              <div className={style.viewCount}>{viewCount}</div>
            </div>
            <div className={style.PostLike}>
              <img
                src={
                  isLiked
                    ? '../../../assets/Community/Like.svg'
                    : '../../../assets/Community/unLike.svg'
                }
                className={style.PostLikeSVG}
                onClick={handleLikeClick}
              />
              <div>좋아요: {likeCount}</div>
            </div>
          </div>
          <div className={style.Buttons}>
            {fetchedData?.userId ===
              (typeof localStorage !== 'undefined' &&
                JSON.parse(localStorage.getItem('UserInfo') ?? '{}')
                  ?.userId) && (
              <Btn
                text='게시글 삭제'
                type='submit'
                className={style.ButtonD}
                onClick={handleDeletePostClick}
              />
            )}
            {isModalOpen && (
              <Modal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onDelete={handleDeletePost}
              />
            )}
            {fetchedData?.userId ===
              (typeof localStorage !== 'undefined' &&
                JSON.parse(localStorage.getItem('UserInfo') ?? '{}')
                  ?.userId) && (
              <Btn
                text='게시글 수정'
                type='submit'
                className={style.ButtonU}
                onClick={handlePatch}
              />
            )}
          </div>
        </div>
        <div className={style.Content}>
          <div className={style.tag}>
            {fetchedData?.tags.map((tag) => (
              <span className={style.tagItem}>{tag.tagName}</span>
            ))}
          </div>
          {fetchedData?.boardImageUrl && (
            <div className={style.Img_Line}>
              <img
                src={fetchedData?.boardImageUrl}
                className={style.Content_Img}
              />
            </div>
          )}

          {/* 게시글 텍스트 */}

          <div className={style.Content_Text}>
            <div
              className='ql-editor'
              dangerouslySetInnerHTML={{
                __html: fetchedData?.text
              }}
            />
          </div>
        </div>
        <div className={style.CommentWrite}>
          <div className={style.CommnetSVG}>
            <img src='../../../assets/Community/Comment.svg' />
            <div className={style.CommnetText}> 댓글 </div>
          </div>
          <input
            className={style.CommentWriteBox}
            type='text'
            placeholder='write your comment'
            value={writeCommnet}
            onChange={(e) => setWriteCommnet(e.target.value)}
            onKeyDown={pressEnter}
          />
          <img
            src='../../../assets/Community/Write.svg'
            onClick={handleClick}
            style={{ cursor: 'pointer' }}
          />
        </div>
        <div className={style.Comment}>
          {comments.map((comment) => (
            <div key={comment.id} className={style.userComment}>
              <div className={style.userCommentInfo}>
                {comment.userProfileUrl ? (
                  <img
                    src={comment.userProfileUrl}
                    className={style.commentImage}
                  />
                ) : (
                  <img
                    src={'../../../assets/Community/UserProfile.svg'}
                    className={style.commentImage}
                  />
                )}
                <div className={style.userNickname}>{comment.userNickname}</div>
              </div>
              <div className={style.commentBody}>
                <div>{comment.commentBody}</div>
                {comment.userId ===
                  (typeof localStorage !== 'undefined' &&
                    JSON.parse(localStorage.getItem('UserInfo') ?? '{}')
                      ?.userId) && (
                  <img
                    src={'../../../assets/Community/Delete.svg'}
                    className={style.deleteSVG}
                    onClick={() => handleDeleteComment(comment.commentId)}
                  />
                )}
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default DetailP;
