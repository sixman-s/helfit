import React, { useState } from 'react';
import Router from 'next/router';
import style from '../../styles/Community/C_Community.module.css';

const SearchBar = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [userProfile, setUserProfile] = useState(
    '../assets/Community/UserProfile.svg'
  );
  const [userName, setUserName] = useState('User');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    Router.push(`/search?q=${searchTerm}`);
    setSearchTerm('');
  };
  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSubmit(e);
    }
  };
  // user의 프로필 사진 데이터를 받아왔을 때 해당 데이터로 대체
  const handleUserProfile = (userProfileData: string) => {
    if (userProfileData) {
      setUserProfile(userProfileData);
    }
  };

  // user의 닉네임을 받아왔을 때 해당 닉네임으로 대체
  const handleUserName = (userNameData: string) => {
    if (userNameData) {
      setUserName(userNameData);
    }
  };

  return (
    <div className={style.Search_box}>
      <div>
        <form onSubmit={handleSubmit}>
          <input
            className={style.Search_bar}
            type='text'
            placeholder='Search anithing...'
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <button type='submit' className={style.SeachBtn}>
            <img src='../assets/Community/Search.svg' />
          </button>
        </form>
      </div>
      <div className={style.UserProfile}>
        <img
          src={userProfile}
          className={style.UserPhoto}
          onError={() => setUserProfile('../assets/Community/UserProfile.svg')} // 이미지 로딩 실패 시 기본 이미지로 대체
        />
        <div className={style.UserName}>{userName}</div>
      </div>
    </div>
  );
};

export default SearchBar;
