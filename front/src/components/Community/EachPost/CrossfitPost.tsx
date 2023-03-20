import React, { useState, useEffect } from 'react';
import Link from 'next/link';
import style from '../../../styles/Community/C_Post.module.css';
import Btn from '../../loginc/Buttons';
import { Pagination } from 'semantic-ui-react';
import axios from 'axios';

interface Post {
  boardId: number;
  boardImageUrl: string | null;
  createdAt: string;
  modifiedAt: string;
  tags: { tagId: number; tagName: string }[];
  text: string;
  title: string;
}
type Props = {
  posts: Post[];
};
const URL = process.env.NEXT_PUBLIC_URL;

const HealthPost: React.FC = () => {
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/2?page=1`)
      .then((res) => setFetchedPosts(res.data))
      .catch((err) => console.log(err));
  }, []);

  const PostCard: React.FC<{ post: Post; order: number }> = ({
    post,
    order
  }) => {
    const { title, tags, createdAt } = post;

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
          <div className={style.nickName}>닉네임</div>
          <div className={style.date}>{createdAtString}</div>
          <div className={style.tag}>
            {tags.map((tag) => tag.tagName).join(', ')}
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
                <Link href={`/community/crossfit/${post.boardId}`}>
                  <PostCard post={post} order={index + 1} />
                </Link>
              </li>
            ))}
          </ul>
        </div>
        <div className={style.pagenation}>
          <Pagination
            defaultActivePage={1}
            firstItem={null}
            lastItem={null}
            pointing
            secondary
            totalPages={7}
          />
        </div>
      </div>
    </>
  );
};

export default HealthPost;
