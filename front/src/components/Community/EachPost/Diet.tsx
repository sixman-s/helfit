import style from '../../../styles/Community/C_Oww.module.css';
import axios from 'axios';
import { useRouter } from 'next/router';
import React, { useState, useEffect } from 'react';
import Btn from '@/components/loginc/Buttons';
import { Pagination, PaginationProps } from 'semantic-ui-react';

interface Post {
  boardId: number;
  boardImageUrl: string | null;
  createdAt: string;
  modifiedAt: string;
  tags: { tagId: number; tagName: string }[];
  text: string;
  title: string;
  userNickname: string;
  view: number;
  likesCount: number;
  userProfileImage: string | null;
}
type Props = {
  posts: Post[];
};
const URL = process.env.NEXT_PUBLIC_URL;

const Oww: React.FC = () => {
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);
  const [activePage, setActivePage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [userProfile, setUserProfile] = useState(
    '../../assets/Community/UserProfile.svg'
  );
  const router = useRouter();
  const handlePageChange = (
    event: React.MouseEvent<HTMLAnchorElement>,
    data: PaginationProps
  ) => {
    setActivePage(data.activePage as number);
  };

  const handlePostView = (post: Post) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/diet/${post.boardId}`))
      .catch((err) => alert(err));
  };

  const handlePostBtn = () => {
    if (typeof localStorage.accessToken !== 'undefined') {
      router.push('/community/writepost');
    } else {
      alert('로그인 한 유저만 게시글을 작성할 수 있습니다.');
    }
  };

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/6?page=${activePage}`)
      .then((res) => {
        setFetchedPosts(res.data.boardResponses);
        setTotalPages(Math.ceil(res.data.count / 10));
      })
      .catch((err) => console.log(err));
  }, [activePage]);

  const PostCard: React.FC<{ post: Post; order: number }> = ({
    post,
    order
  }) => {
    const {
      title,
      userNickname,
      view,
      boardImageUrl,
      likesCount,
      userProfileImage
    } = post;

    return (
      <div>
        <ul>
          <li className={style.SNSbox}>
            <div className={style.postUser}>
              <img
                src={
                  userProfileImage || '../../assets/Community/UserProfile.svg'
                }
                className={style.UserPhoto}
                onError={() =>
                  setUserProfile('../../assets/Community/UserProfile.svg')
                }
              />
              <div className={style.postUserName}>{userNickname}</div>
            </div>
            <div className={style.photo}>
              {boardImageUrl && (
                <img src={boardImageUrl} className={style.Content_Img} />
              )}
            </div>
            <div className={style.title}>{title}</div>
            <div className={style.postInfo}>
              <div className={style.postLike}>
                <img src='../../assets/Community/Like.svg' />
                <div className={style.postInfoText}>좋아요: {likesCount}</div>
              </div>
              <div className={style.postComment}>
                <img src='../../assets/Community/Comment.svg' />
                <div className={style.postInfoText}>댓글</div>
              </div>
              <div className={style.viewCount}>
                <div className={style.postInfoText}>조회수 : {view}</div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    );
  };

  return (
    <>
      <div className={style.container}>
        <div className={style.buttonline}>
          <Btn
            className={style.button}
            text='게시글 작성'
            onClick={handlePostBtn}
          />
        </div>
        <ul className={style.ul}>
          {Array.isArray(fetchedPosts) && fetchedPosts.length > 0 ? (
            fetchedPosts.map((post, index) => (
              <li key={post.boardId} onClick={handlePostView(post)}>
                <PostCard post={post} order={index + 1} />
              </li>
            ))
          ) : (
            <div className={style.noPost}>게시글을 작성해주세요</div>
          )}
        </ul>

        <div className={style.pagenation}>
          <Pagination
            activePage={activePage}
            onPageChange={handlePageChange}
            firstItem={null}
            lastItem={null}
            pointing
            secondary
            totalPages={totalPages}
            className={style.PagenationDetail}
          />
        </div>
      </div>
    </>
  );
};
export default Oww;
