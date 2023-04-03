import axios from 'axios';
import { Router, useRouter } from 'next/router';
import { ReactNode, useEffect, useState } from 'react';
import s from '../../../styles/mypage/C_Board.module.css';
import Checkbox from './Checkbox';

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
  categoryId: number;
}

const MyBoard = (): JSX.Element => {
  const [count, setCount] = useState(1);
  const [info, setInfo] = useState<BoardData[]>([]);
  const [totalCount, setTotalCount] = useState(0);

  const router = useRouter();

  const escapeMap = {
    '&lt;': '<',
    '&#12296;': '<',
    '&gt;': '>',
    '&#12297;': '>',
    '&amp;': '&',
    '&quot;': '"',
    '&#x27;': "'"
  };
  const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;
  const convertToHtml = (text) =>
    text.replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match);

  useEffect(() => {
    const url = process.env.NEXT_PUBLIC_URL;
    const accessToken = localStorage.getItem('accessToken');

    if (count <= 0) {
      setCount(1);
    }

    axios
      .get(
        `${url}/api/v1/board/users?page=${count}`,

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

        setInfo(res.data.boardResponses);
        return info;
      });
  }, [count]);

  const beforePage = () => {
    if (count > 1) setCount(count - 1);
  };

  const nextPage = () => {
    if (count * 5 < totalCount) setCount(count + 1);
  };

  const myPostView = (el: BoardData) => () => {
    const url = process.env.NEXT_PUBLIC_URL;

    const categoryMapper = {
      1: 'health',
      2: 'crossfit',
      4: 'pilates',
      5: 'oww',
      6: 'diet'
    };

    const category = categoryMapper[el.categoryId];

    axios
      .get(`${url}/api/v1/board/${el.categoryId}/${el.boardId}`)
      .then(() => router.push(`/community/${category}/${el.boardId}`))
      .catch((err) => alert(err));
  };

  return (
    <div className={s.BoardContainer}>
      <div className={s.topLine}>
        <div className={s.title}>Boards</div>
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
      {info.length === 0 ? (
        <Checkbox />
      ) : (
        info.map((el, idx) => {
          return (
            <div className={s.board} key={idx}>
              <div className={s.boardTitle} onClick={myPostView(el)}>
                {el.title}
              </div>
              <div
                className={s.boardContent}
                dangerouslySetInnerHTML={{
                  __html: convertToHtml(el.text)
                }}
              />
            </div>
          );
        })
      )}
    </div>
  );
};

export default MyBoard;
