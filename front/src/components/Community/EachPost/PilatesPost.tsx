import React, { useState, useEffect } from 'react';
import Link from 'next/link';
import style from '../../../styles/Community/C_Post.module.css';
import Btn from '../../loginc/Buttons';
import { Pagination, PaginationProps } from 'semantic-ui-react';
import axios from 'axios';

interface Post {
  boardId: number;
  boardImageUrl: string | null;
  createdAt: string;
  modifiedAt: string;
  tags: { tagId: number; tagName: string }[];
  text: string;
  title: string;
  userNickname: string;
}
type Props = {
  posts: Post[];
};
const URL = process.env.NEXT_PUBLIC_URL;

const HealthPost: React.FC = () => {
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);
  const [activePage, setActivePage] = useState(1);

  const handlePageChange = (
    event: React.MouseEvent<HTMLAnchorElement>,
    data: PaginationProps
  ) => {
    setActivePage(data.activePage as number);
  };

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/4?page=${activePage}`)
      //.then((res) => console.log(res))
      .then((res) => setFetchedPosts(res.data))
      .catch((err) => console.log(err));
  }, [activePage]);

  const PostCard: React.FC<{ post: Post; order: number }> = ({
    post,
    order
  }) => {
    const { title, tags, createdAt, userNickname } = post;

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
          <div className={style.views}>조회수</div>
        </li>
      </div>
    );
  };

  return (
    <>
      <div className={style.List}>
        <div className={style.buttonline}>
          <Link href={'/community/writepost'}>
            <Btn className={style.button} text='게시글 작성' />
          </Link>
        </div>
        <div className={style.ListHeader}>
          <div className={style.ListHeader_No}>No.</div>
          <div className={style.ListHeader_Title}>제목</div>
          <div className={style.ListHeader_nickName}>작성자</div>
          <div className={style.ListHeader_Date}>작성일</div>
          <div className={style.ListHeader_Tag}>태그</div>
          <div className={style.ListHeader_Views}>조회수</div>
        </div>
        <div className={style.ListBody}>
          <ul>
            {fetchedPosts.map((post, index) => (
              <li key={post.boardId}>
                <Link href={`/community/pilates/${post.boardId}`}>
                  <PostCard post={post} order={index + 1} />
                </Link>
              </li>
            ))}
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
            totalPages={5}
          />
        </div>
      </div>
    </>
  );
};

export default HealthPost;
