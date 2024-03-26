import style from '../../styles/Community/C_Community.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';

interface BoardData {
  boardId: number;
  title: string;
  userId: number;
  userNickname: string;
  view: number;
  boardImageUrl: string | null;
  likesCount: number;
  userProfileImage: string | null;
}

const URL = process.env.NEXT_PUBLIC_URL;

const Popular = (): JSX.Element => {
  const [healthPosts, setHealthPosts] = useState<BoardData[]>([]);
  const [pilatesPosts, setPilatesPosts] = useState<BoardData[]>([]);
  const [crossfitPosts, setCrossfitPosts] = useState<BoardData[]>([]);
  const [owwPosts, setOwwPosts] = useState<BoardData[]>([]);
  const [dietPosts, setDietPosts] = useState<BoardData[]>([]);

  const router = useRouter();

  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/hot/1`)
      .then((res) => setHealthPosts(res.data));
    //.then((res) => console.log(res.data));
    axios
      .get(`${URL}/api/v1/board/hot/4`)
      .then((res) => setPilatesPosts(res.data));
    axios
      .get(`${URL}/api/v1/board/hot/2`)
      .then((res) => setCrossfitPosts(res.data));
    axios.get(`${URL}/api/v1/board/hot/5`).then((res) => {
      setOwwPosts(res.data.slice(0, 3));
    });
    axios
      .get(`${URL}/api/v1/board/hot/6`)
      .then((res) => setDietPosts(res.data));
  }, []);

  const HealthPostView = (post: BoardData) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/health/${post.boardId}`))
      .catch((err) => alert(err));
  };
  const PilatesPostView = (post: BoardData) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/pilates/${post.boardId}`))
      .catch((err) => alert(err));
  };
  const CrossfitPostView = (post: BoardData) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/crossfit/${post.boardId}`))
      .catch((err) => alert(err));
  };
  const OwwPostView = (post: BoardData) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/oww/${post.boardId}`))
      .catch((err) => alert(err));
  };
  const DietPostView = (post: BoardData) => () => {
    console.log(post.boardId);
    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/diet/${post.boardId}`))
      .catch((err) => alert(err));
  };

  return (
    <>
      <div className={style.box}>
        {/* <div className={style.Oww_content}> */}
        <div className={style.P_content}>
          <h4 className={style.Oww_text_h4}>🔥 인기 오운완 게시글</h4>
          {owwPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.SNSContent}>
              {owwPosts.map((post, index) => (
                <li
                  className={style.SNSContent_list}
                  key={post.boardId}
                  onClick={OwwPostView(post)}
                >
                  <div className={style.SNSbody}>
                    <div className={style.SNS_UserNav}>
                      <img
                        src={
                          post.userProfileImage ||
                          '../../assets/Community/UserProfile.svg'
                        }
                        className={style.UserPhoto}
                      />
                      <div className={style.SNS_nickname}>
                        {post.userNickname}
                      </div>
                    </div>
                    <div className={style.SNS_owwPhoto}>
                      <img
                        src={post.boardImageUrl}
                        className={style.oww_photo}
                      />
                    </div>
                    <div className={style.SNS_footer}>
                      <div className={style.SNS_title}>{post.title}</div>
                      <div className={style.SNS_love}>
                        <img
                          src='../../assets/Community/Like.svg'
                          className={style.loveSVG}
                        />
                        <div className={style.SNS_love_Text}>좋아요:</div>
                        <div className={style.SNS_love_count}>
                          {post.likesCount}
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>

      <div className={style.box}>
        <div className={style.P_content}>
          <h4 className={style.P_text_h4}>🔥 인기 헬스 게시글</h4>
          {healthPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {healthPosts.map((post, index) => (
                <li
                  className={style.P_list}
                  key={post.boardId}
                  onClick={HealthPostView(post)}
                >
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
          {pilatesPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {pilatesPosts.map((post, index) => (
                <li
                  className={style.P_list}
                  key={post.boardId}
                  onClick={PilatesPostView(post)}
                >
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
          {crossfitPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {crossfitPosts.map((post, index) => (
                <li
                  className={style.P_list}
                  key={post.boardId}
                  onClick={CrossfitPostView(post)}
                >
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
          {dietPosts.length === 0 ? (
            <div className={style.noneMsg}>게시글을 입력해주세요 </div>
          ) : (
            <ul className={style.allContent}>
              {dietPosts.map((post, index) => (
                <li
                  className={style.P_list}
                  key={post.boardId}
                  onClick={DietPostView(post)}
                >
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
