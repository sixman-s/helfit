import style from '../../../styles/Community/C_Oww.module.css';
import React, { useState, useEffect } from 'react';

const Oww = () => {
  const [userProfile, setUserProfile] = useState(
    '../../assets/Community/UserProfile.svg'
  );

  return (
    <>
      <div className={style.container}>
        <div className={style.postUser}>
          <img
            src={userProfile}
            className={style.UserPhoto}
            onError={() =>
              setUserProfile('../../assets/Community/UserProfile.svg')
            }
          />
          <div className={style.postUserName}>UserNickname</div>
        </div>
        <div className={style.photo}></div>
        <div className={style.postInfo}>
          <div className={style.postLike}>
            <img src='../../assets/Community/Like.svg' />
            <div className={style.postInfoText}>좋아요</div>
          </div>
          <div className={style.postComment}>
            <img src='../../assets/Community/Comment.svg' />
            <div className={style.postInfoText}>댓글</div>
          </div>
        </div>
      </div>

      <div className={style.container}>
        <div className={style.postUser}>
          <img
            src={userProfile}
            className={style.UserPhoto}
            onError={() =>
              setUserProfile('../../assets/Community/UserProfile.svg')
            }
          />
          <div className={style.postUserName}>UserNickname</div>
        </div>
        <div className={style.photo}></div>
        <div className={style.postInfo}>
          <div className={style.postLike}>
            <img src='../../assets/Community/Like.svg' />
            <div className={style.postInfoText}>좋아요</div>
          </div>
          <div className={style.postComment}>
            <img src='../../assets/Community/Comment.svg' />
            <div className={style.postInfoText}>댓글</div>
          </div>
        </div>
      </div>

      <div className={style.container}>
        <div className={style.postUser}>
          <img
            src={userProfile}
            className={style.UserPhoto}
            onError={() =>
              setUserProfile('../../assets/Community/UserProfile.svg')
            }
          />
          <div className={style.postUserName}>UserNickname</div>
        </div>
        <div className={style.photo}></div>
        <div className={style.postInfo}>
          <div className={style.postLike}>
            <img src='../../assets/Community/Like.svg' />
            <div className={style.postInfoText}>좋아요</div>
          </div>
          <div className={style.postComment}>
            <img src='../../assets/Community/Comment.svg' />
            <div className={style.postInfoText}>댓글</div>
          </div>
        </div>
      </div>

      <div className={style.container}>
        <div className={style.postUser}>
          <img
            src={userProfile}
            className={style.UserPhoto}
            onError={() =>
              setUserProfile('../../assets/Community/UserProfile.svg')
            }
          />
          <div className={style.postUserName}>UserNickname</div>
        </div>
        <div className={style.photo}></div>
        <div className={style.postInfo}>
          <div className={style.postLike}>
            <img src='../../assets/Community/Like.svg' />
            <div className={style.postInfoText}>좋아요</div>
          </div>
          <div className={style.postComment}>
            <img src='../../assets/Community/Comment.svg' />
            <div className={style.postInfoText}>댓글</div>
          </div>
        </div>
      </div>
    </>
  );
};
export default Oww;
