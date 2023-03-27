import React, { useState } from 'react';
import style from '../../styles/Community/C_Community.module.css';
import UserNav from './C_Community/UserNav';
import { useRouter } from 'next/router';

const SearchBar = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchType, setSearchType] = useState('title');

  const router = useRouter();
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    router.push(`/community/result/${searchType}?${searchType}=${searchTerm}`);
    setSearchTerm('');
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSubmit(e);
    }
  };

  return (
    <div className={style.Search_box}>
      <div className={style.Search_bar_line}>
        <form onSubmit={handleSubmit}>
          <input
            className={style.Search_bar}
            type='text'
            placeholder='검색 유형을 선택해주세요 '
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyDown={handleKeyDown}
          />
        </form>
        <select
          value={searchType}
          className={style.searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value='title'>Title</option>
          <option value='text'>Text</option>
          <option value='tag'>Tag</option>
          <option value='nickname'>Nickname</option>
        </select>
        <button type='submit' className={style.SeachBtn} onClick={handleSubmit}>
          <img src='../../assets/Community/Search.svg' />
        </button>
      </div>
      <div className={style.UserProfile}>
        <UserNav />
      </div>
    </div>
  );
};

export default SearchBar;
