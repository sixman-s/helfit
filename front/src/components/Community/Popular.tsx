import style from '../../styles/Community/C_Community.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';

interface BoardData {
  boardId: number;
  title: string;
  userId: number;
  userNickname: string;
  view: number;
}

const URL = process.env.NEXT_PUBLIC_URL;

const Popular = (): JSX.Element => {
  const [healthPosts, setHealthPosts] = useState<BoardData[]>([]);
  const [pilatesPosts, setPilatesPosts] = useState<BoardData[]>([]);
  const [crossfitPosts, setCrossfitPosts] = useState<BoardData[]>([]);
  const [owwPosts, setOwwPosts] = useState<BoardData[]>([]);
  const [dietPosts, setDietPosts] = useState<BoardData[]>([]);

  const HealthFourPosts = healthPosts
    .sort((a, b) => b.view - a.view)
    .slice(0, 4);
  const PilatesFourPosts = pilatesPosts
    .sort((a, b) => b.view - a.view)
    .slice(0, 4);
  const CrossfitFourPosts = crossfitPosts
    .sort((a, b) => b.view - a.view)
    .slice(0, 4);
  const OwwFourPosts = owwPosts.sort((a, b) => b.view - a.view).slice(0, 4);
  const DietFourPosts = dietPosts.sort((a, b) => b.view - a.view).slice(0, 4);

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/1?page=1`)
      .then((res) => setHealthPosts(res.data.boardResponses));
    axios
      .get(`${URL}/api/v1/board/4?page=1`)
      .then((res) => setPilatesPosts(res.data.boardResponses));
    axios
      .get(`${URL}/api/v1/board/2?page=1`)
      .then((res) => setCrossfitPosts(res.data.boardResponses));
    axios
      .get(`${URL}/api/v1/board/5?page=1`)
      .then((res) => setOwwPosts(res.data.boardResponses));
    axios
      .get(`${URL}/api/v1/board/6?page=1`)
      .then((res) => setDietPosts(res.data.boardResponses));
  }, []);

  return (
    <>
      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 헬스 게시글</h4>
          {HealthFourPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {HealthFourPosts.map((post, index) => (
                <li className={style.P_list} key={post.boardId}>
                  <div>{index + 1}.</div>
                  <div className={style.PostContent}>{post.title}</div>
                  <div className={style.nickname}>{post.userNickname}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>

      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 필라테스 게시글</h4>
          {PilatesFourPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {PilatesFourPosts.map((post, index) => (
                <li className={style.P_list} key={post.boardId}>
                  <div>{index + 1}.</div>
                  <div className={style.PostContent}>{post.title}</div>
                  <div className={style.nickname}>{post.userNickname}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 크로스핏 게시글</h4>
          {CrossfitFourPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {CrossfitFourPosts.map((post, index) => (
                <li className={style.P_list} key={post.boardId}>
                  <div>{index + 1}.</div>
                  <div className={style.PostContent}>{post.title}</div>
                  <div className={style.nickname}>{post.userNickname}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 오운완 게시글</h4>

          {OwwFourPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {OwwFourPosts.map((post, index) => (
                <li className={style.P_list} key={post.boardId}>
                  <div>{index + 1}.</div>
                  <div className={style.PostContent}>{post.title}</div>
                  <div className={style.nickname}>{post.userNickname}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 식단 게시글</h4>

          {DietFourPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {DietFourPosts.map((post, index) => (
                <li className={style.P_list} key={post.boardId}>
                  <div>{index + 1}.</div>
                  <div className={style.PostContent}>{post.title}</div>
                  <div className={style.nickname}>{post.userNickname}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </>
  );
};

export default Popular;
