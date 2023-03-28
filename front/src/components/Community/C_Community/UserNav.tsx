import React, { useState, useEffect } from 'react';
import style from '../../../css/Community/C_Community.module.css';

const UserNav = () => {
  const [userName, setUserName] = useState('User');
  const [userProfile, setUserProfile] = useState(
    '../../assets/Community/UserProfile.svg'
  );

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const userInfo = JSON.parse(localStorage.getItem('UserInfo'));
      if (userInfo) {
        // add null check here
        setUserName(userInfo.nickname);
        setUserProfile(userInfo.profileImageUrl);
      }
    }
  }, []);

  return (
    <>
      <div className={style.UserProfile}>
        <div>
          <img src={userProfile} className={style.UserPhoto} />
        </div>
        <div className={style.UserName1}>{userName}</div>
      </div>
    </>
  );
};
export default UserNav;
