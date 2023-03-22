import style from '../../../styles/Community/C_Oww.module.css';
import axios from 'axios';
import { useRouter } from 'next/router';
import React, { useState, useEffect } from 'react';
import Btn from '@/components/loginc/Buttons';
import Link from 'next/link';
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
}
type Props = {
  posts: Post[];
};
const URL = process.env.NEXT_PUBLIC_URL;

const Oww: React.FC = () => {
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);
  const [activePage, setActivePage] = useState(1);
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
      .then(() => router.push(`/community/oww/${post.boardId}`))
      .catch((err) => alert(err));
  };

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/5?page=${activePage}`)
      //.then((res) => console.log(res.data))
      .then((res) => setFetchedPosts(res.data))
      .catch((err) => console.log(err));
  }, [activePage]);

  const PostCard: React.FC<{ post: Post; order: number }> = ({
    post,
    order
  }) => {
    const { title, userNickname, view, boardImageUrl } = post;

    return (
      <div>
        <ul>
          <li className={style.SNSbox}>
            <div className={style.postUser}>
              <img
                src={userProfile}
                className={style.UserPhoto}
                onError={() =>
                  setUserProfile('../../assets/Community/UserProfile.svg')
                }
              />
              <div className={style.postUserName}>{userNickname}</div>
            </div>
            <div className={style.photo}>{boardImageUrl}</div>
            <div className={style.title}>{title}</div>
            <div className={style.postInfo}>
              <div className={style.postLike}>
                <img src='../../assets/Community/Like.svg' />
                <div className={style.postInfoText}>좋아요</div>
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
          <Link href={'/community/writepost'}>
            <Btn className={style.button} text='게시글 작성' />
          </Link>
        </div>
        <ul className={style.ul}>
          {fetchedPosts.map((post, index) => (
            <li key={post.boardId} onClick={handlePostView(post)}>
              <PostCard post={post} order={index + 1} />
            </li>
          ))}
        </ul>

        <div className={style.pagenation}>
          <Pagination
            activePage={activePage}
            onPageChange={handlePageChange}
            firstItem={null}
            lastItem={null}
            pointing
            secondary
            totalPages={5}
          />
        </div>
      </div>
    </>
  );
};
export default Oww;
