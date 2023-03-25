import style from '../../styles/Community/C_Community.module.css';

interface Category {
  title: string;
  name: string;
}

interface Post {
  id: number;
  content: string;
  nickname: string;
}

const categories: Category[] = [
  { title: '헬스', name: 'health' },
  { title: '필라테스', name: 'pilates' },
  { title: '크로스핏', name: 'crossfit' },
  { title: '오운완', name: 'owunwan' },
  { title: '식단', name: 'diet' }
];

const posts: { [key: string]: Post[] } = {
  health: [
    {
      id: 1,
      content:
        '아일너ㅏ림ㄴ어리ㅏㅇㄴㅁ러아ㅣ러나ㅣㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ ',
      nickname: '신림동 핑크덤벨'
    },
    { id: 2, content: '헬스 게시글 ', nickname: 'user2' },
    { id: 3, content: '헬스 게시글 ', nickname: 'user3' },
    { id: 4, content: '헬스 게시글 ', nickname: 'user4' }
  ],
  pilates: [
    { id: 1, content: '필라테스 게시글 ', nickname: 'user1' },
    { id: 2, content: '필라테스 게시글 ', nickname: 'user2' },
    { id: 3, content: '필라테스 게시글 ', nickname: 'user3' },
    { id: 4, content: '필라테스 게시글 ', nickname: 'user4' }
  ],
  crossfit: [
    { id: 1, content: '크로스핏 게시글 ', nickname: 'user1' },
    { id: 2, content: '크로스핏 게시글 ', nickname: 'user2' },
    { id: 3, content: '크로스핏 게시글 ', nickname: 'user3' },
    { id: 4, content: '크로스핏 게시글 ', nickname: 'user4' }
  ],
  owunwan: [
    { id: 1, content: '오운완 게시글 ', nickname: 'user1' },
    { id: 2, content: '오운완 게시글 ', nickname: 'user2' },
    { id: 3, content: '오운완 게시글 ', nickname: 'user3' },
    { id: 4, content: '오운완 게시글 ', nickname: 'user4' }
  ],
  diet: [
    { id: 1, content: '식단 게시글 ', nickname: 'user1' },
    { id: 2, content: '식단 게시글 ', nickname: 'user2' },
    { id: 3, content: '식단 게시글 ', nickname: 'user3' },
    { id: 4, content: '식단 게시글 ', nickname: 'user4' }
  ]
};

const Popular = (): JSX.Element => {
  return (
    <>
      {categories.map((category: Category) => (
        <div className={style.box} key={category.name}>
          <div className={style.P_content}>
            <h4 className={style.P_text_h4}>🔥 인기 {category.title} 게시글</h4>
            {posts[category.name].map((post) => (
              <li className={style.P_list} key={post.id}>
                <div>{post.id}.</div>
                <div className={style.PostContent}>{post.content}</div>
                <div className={style.nickname}>{post.nickname}</div>
              </li>
            ))}
          </div>
        </div>
      ))}
    </>
  );
};

export default Popular;
