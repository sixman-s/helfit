import React, { useState, useEffect } from 'react';
import style from '../../../styles/Community/C_Post.module.css';
import Btn from '../../loginc/Buttons';
import { Pagination, PaginationProps } from 'semantic-ui-react';
import axios from 'axios';
import { useRouter } from 'next/router';

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

const HealthPost: React.FC = () => {
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);
  const [activePage, setActivePage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
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
      .then(() => router.push(`/community/health/${post.boardId}`))
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
      .get(`${URL}/api/v1/board/1?page=${activePage}`)
      //.then((res) => console.log(res.data.count))
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
    const { title, tags, createdAt, userNickname, view } = post;

    const createdAtString = new Date(createdAt)
      .toLocaleDateString('en-KR', {
        year: '2-digit',
        month: '2-digit',
        day: '2-digit'
      })
      .split('/')
      .join('.');

    return (
      <div>
        <ul>
          <li className={style.ListItem}>
            <div className={style.No}>{order}.</div>
            <div className={style.title}>{title}</div>
            <div className={style.author}>{userNickname}</div>
            <div className={style.date}>{createdAtString}</div>
            <div className={style.tag}>
              {tags.map((tag) => (
                <span className={style.tagItem}>{tag.tagName}</span>
              ))}
            </div>
            <div className={style.views}>{view}</div>
          </li>
        </ul>
      </div>
    );
  };

  return (
    <>
      <div className={style.List}>
        <div className={style.buttonline}>
          <Btn
            className={style.button}
            text='게시글 작성'
            onClick={handlePostBtn}
          />
        </div>
        <div className={style.ListHeader}>
          <div className={style.ListHeader_No}>No.</div>
          <div className={style.ListHeader_Title}>제목</div>
          <div className={style.ListHeader_Author}>작성자</div>
          <div className={style.ListHeader_Date}>작성일</div>
          <div className={style.ListHeader_Tag}>태그</div>
          <div className={style.ListHeader_Views}>조회수</div>
        </div>
        <div className={style.ListBody}>
          <ul>
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
        </div>
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

export default HealthPost;
