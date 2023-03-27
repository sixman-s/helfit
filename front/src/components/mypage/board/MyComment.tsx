import axios from 'axios';
import { useEffect, useState } from 'react';
import s from '../../../styles/mypage/C_Comment.module.css';
import Pagenation from '../Pagination';

interface BoardData {
  boardId: number;
  title: string;
  userId: number;
  userNickname: string;
  view: number;
  boardImageUrl: string | null;
  likesCount: number;
  userProfileImage: string | null;
  text: string | null;
  commentBody: string;
}

const url = process.env.NEXT_PUBLIC_URL;
const accessToken = localStorage.getItem('accessToken');

const MyComment = () => {
  const [count, setCount] = useState(1);
  const [info, setInfo] = useState<BoardData[]>([]);
  const [totalCount, setTotalCount] = useState(0);

  useEffect(() => {
    if (count <= 0) {
      setCount(1);
      console.log(count);
    }
    console.log(count);

    axios
      .get(
        `${url}/api/v1/comment/users?page=${count}`,

        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        }
      )
      .then((res) => {
        const {
          data: { count }
        } = res;
        setTotalCount(count);

        setInfo(res.data.commentResponses);
        console.log(res.data.commentResponses);
        return info;
      });
  }, [count]);

  const beforePage = () => {
    if (count > 1) setCount(count - 1);
  };

  const nextPage = () => {
    if (count * 5 < totalCount) setCount(count + 1);
  };

  return (
    <div className={s.commentContainer}>
      <div className={s.topLine}>
        <div className={s.title}>MyComments</div>
        <div>
          <img
            id={s.left}
            onClick={beforePage}
            src='../../../../assets/mypage/left.svg'
          ></img>
          <img
            id={s.right}
            onClick={nextPage}
            src='../../../../assets/mypage/right.svg'
          ></img>
        </div>
      </div>
      {info.length === 0
        ? '게시물을 등록해주세요!'
        : info.map((el, idx) => {
            return (
              <div className={s.comment} key={idx}>
                {/* <div className={s.commentTitle}>{el.title}</div> */}
                <div className={s.commentContent}>{el.commentBody}</div>
              </div>
            );
          })}
    </div>
  );
};

export default MyComment;
