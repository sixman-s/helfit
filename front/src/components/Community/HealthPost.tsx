import React from 'react';
import style from '../../styles/Community/C_Post.module.css';
import Btn from '../Login/Buttons';
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

const HealthPost: React.FC<Props> = ({ posts }) => (
  <>
    <div className={style.List}>
      <Btn
        className={style.button}
        text='게시글 작성'
        onClick={() => console.log('게시글 작성 버튼을 눌렀습니다.')}
      />
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
            <li key={post.id} className={style.ListItem}>
              <div className={style.No}>{post.id}.</div>
              <div className={style.title}>{post.title}</div>
              <div className={style.author}>{post.author}</div>
              <div className={style.date}>{post.date}</div>
              <div className={style.tag}>{post.tag}</div>
              <div className={style.views}>{post.views}</div>
            </li>
          ))}
        </ul>
      </div>
      <div className={style.pagenation}>
        <Pagenation />
      </div>
    </div>
  </>
);

export default HealthPost;
