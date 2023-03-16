import style from '../../styles/Community/P_Detail.module.css';
import React, { useState } from 'react';
import UserNav from './C_Community/UserNav';

const DetailP = () => {
  const [writeCommnet, setWriteCommnet] = useState('');

  const handleSubmit = (e: React.KeyboardEvent) => {
    // 댓글 작성 후 서버에 전송하는 로직 작성
    console.log('Comment submitted:', writeCommnet);
    setWriteCommnet('');
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

  const imgSrc = '../../assets/Community/하입보이.png';

  return (
    <>
      <div className={style.allInput}>
        <div className={style.UserProfile}>
          <UserNav />
        </div>
        <div className={style.Category}>헬스 갤러리</div>
        <div className={style.Title}>홍대 헬스장 가려면 어디로 가야 해요? </div>
        <div className={style.PostNav}>
          <div>작성자</div>
          <div>createdAt</div>
          <div>조회수</div>
          <div className={style.PostLike}>
            <img
              src='../../assets/Community/Like.svg'
              className={style.PostLikeSVG}
            />
            <div>좋아요</div>
          </div>
        </div>
        <div className={style.Content}>
          <div className={style.Content_Text}>
            뉴진스의 Hype Boy 빼고 알려주세요 제발
          </div>
          {imgSrc && (
            <div style={{ width: '250px', height: '250px' }}>
              <img src={imgSrc} className={style.Content_Img} />
            </div>
          )}
          {!imgSrc && <div style={{ display: 'none' }}></div>}
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
