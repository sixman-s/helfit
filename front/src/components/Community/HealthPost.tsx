import React from 'react';
import Link from 'next/link';
import style from '../../styles/Community/C_Post.module.css';
import Btn from '../loginc/Buttons';
import Pagenation from './C_Community/Pagenation';

type Post = {
  id: number;
  title: string;
  author: string;
  date: string;
  tag: string;
  views: string;
};

type Props = {
  posts: Post[];
};

const HealthPost: React.FC<Props> = ({ posts }) => {
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
          <div className={style.ListHeader_Author}>작성자</div>
          <div className={style.ListHeader_Date}>작성일</div>
          <div className={style.ListHeader_Tag}>태그</div>
          <div className={style.ListHeader_Views}>조회수</div>
        </div>
        <div className={style.ListBody}>
          <ul>
            {posts.map((post) => (
              <Link href={`/community/health/${post.id}`} key={post.id}>
                <li className={style.ListItem}>
                  <div className={style.No}>{post.id}.</div>
                  <div className={style.title}>{post.title}</div>
                  <div className={style.author}>{post.author}</div>
                  <div className={style.date}>{post.date}</div>
                  <div className={style.tag}>{post.tag}</div>
                  <div className={style.views}>{post.views}</div>
                </li>
              </Link>
            ))}
          </ul>
        </div>
        <div className={style.pagenation}>
          <Pagenation />
        </div>
      </div>
    </>
  );
};

export default HealthPost;
