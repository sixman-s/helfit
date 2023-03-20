import style from '../../styles/Community/P_Detail.module.css';
import React, { useState, useEffect } from 'react';
import UserNav from './C_Community/UserNav';
import axios from 'axios';
import { useRouter } from 'next/router';
import Btn from '../loginc/Buttons';
import Modal from './C_Community/DeletePost';

interface BoardData {
  boardId: number;
  title: string;
  text: string;
  boardImageUrl: string | null;
  tags: {
    tagId: number;
    tagName: string;
  }[];
  createdAt: string;
  modifiedAt: string;
}

const DetailP = () => {
  const URL = process.env.NEXT_PUBLIC_URL;
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [writeCommnet, setWriteCommnet] = useState('');
  const [fetchedData, setFetchedData] = useState<BoardData | null>(null);
  const handleSubmit = (e: React.KeyboardEvent) => {
    // 댓글 작성 후 서버에 전송하는 로직 작성
    console.log('Comment submitted:', writeCommnet);
    setWriteCommnet('');
  };

  const handleDeletePostClick = () => {
    setIsModalOpen(true);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSubmit(e);
    }
  };
  const handleClick = (e: React.MouseEvent<HTMLImageElement>) => {
    const keyboardEvent = e as unknown as React.KeyboardEvent;
    handleSubmit(keyboardEvent);
    console.log('Click!');
  };
  const createdAtString = new Date(fetchedData?.createdAt)
    .toLocaleDateString('en-KR', {
      year: '2-digit',
      month: '2-digit',
      day: '2-digit'
    })
    .split('/')
    .join('.');
  const router = useRouter();
  const { id } = router.query;
  const boardID = id ? parseInt(id[id.length - 1]) : null;
  const currentPage = router.asPath.split('/')[2];

  let pageNumber: Number;
  switch (currentPage) {
    case 'health':
      pageNumber = 1;
      break;
    case 'crossfit':
      pageNumber = 2;
      break;
    case 'pilates':
      pageNumber = 4;
      break;
    default:
      pageNumber = null;
  }

  useEffect(() => {
    const userID: number = JSON.parse(localStorage.UserInfo).userId;
    axios
      .get(`${URL}/api/v1/board/${pageNumber}/${userID}/${boardID}`)
      .then((res) => setFetchedData(res.data))
      .catch((err) => console.log(err));
  }, [boardID]);

  return (
    <>
      <div className={style.allInput}>
        <div className={style.UserProfile}>
          <UserNav />
        </div>
        <div className={style.Category}>헬스 갤러리</div>
        <div className={style.Title}>{fetchedData?.title}</div>
        <div className={style.Nav}>
          <div className={style.PostNav}>
            <div>작성자</div>
            <div>{createdAtString}</div>
            <div>조회수</div>
            <div className={style.PostLike}>
              <img
                src='../../assets/Community/Like.svg'
                className={style.PostLikeSVG}
              />
              <div>좋아요</div>
            </div>
          </div>
          <div className={style.Buttons}>
            <Btn
              text='게시글 삭제'
              type='submit'
              className={style.ButtonD}
              onClick={handleDeletePostClick}
            />
            {isModalOpen && (
              <Modal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
              />
            )}

            <Btn text='게시글 수정' type='submit' className={style.ButtonU} />
          </div>
        </div>
        <div className={style.Content}>
          <div>
            <div className={style.tag}>
              {fetchedData?.tags.map((tag) => (
                <span className={style.tagItem}>{tag.tagName}</span>
              ))}
            </div>
            <div className={style.Content_Text}>{fetchedData?.text}</div>
          </div>
          {fetchedData?.boardImageUrl && (
            <div style={{ width: '250px', height: '250px' }}>
              <img
                src={fetchedData?.boardImageUrl}
                className={style.Content_Img}
              />
            </div>
          )}
        </div>
        <div className={style.CommentWrite}>
          <div className={style.CommnetSVG}>
            <img src='../../assets/Community/Comment.svg' />
            <div className={style.CommnetText}> 댓글 </div>
          </div>
          <input
            className={style.CommentWriteBox}
            type='text'
            placeholder='write your comment'
            value={writeCommnet}
            onChange={(e) => setWriteCommnet(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <img
            src='../../assets/Community/Write.svg'
            onClick={handleClick}
            style={{ cursor: 'pointer' }}
          />
        </div>
        <div className={style.Commnet}>
          <div> 댓글 </div>
          <div> 댓글 박스</div>
        </div>
      </div>
    </>
  );
};

export default DetailP;
