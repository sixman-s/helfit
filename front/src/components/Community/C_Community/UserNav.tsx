import React, { useState, useEffect } from 'react';
import style from '../../../styles/Community/C_Community.module.css';

const UserNav = () => {
  const [userName, setUserName] = useState('User');
  const [userProfile, setUserProfile] = useState(
    '../../../../assets/Community/UserProfile.svg'
  );

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const userInfo = JSON.parse(localStorage.getItem('UserInfo'));
      if (userInfo) {
        setUserName(userInfo.nickname);
      }
      if (userInfo.profileImageUrl !== null) {
        setUserProfile(userInfo.profileImageUrl);
      } else if (userInfo.profileImageUrl == null) {
        setUserProfile(userProfile);
      }
    }
  }, []);

  return (
    <>
      <div className={style.UserProfile}>
        <div>
          <img
            src={userProfile || '../../../../assets/Community/UserProfile.svg'}
            className={style.UserPhoto}
          />
        </div>
        <div className={style.UserName1}>{userName}</div>
      </div>
    </>
  );
};
export default UserNav;
