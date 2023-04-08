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
      e.preventDefault();
      handleSubmit(e);
    }
  };

  const pressEnter: React.KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.nativeEvent.isComposing) {
      return;
    }

    if (e.key === 'Enter' && e.shiftKey) {
      return;
    } else if (e.key === 'Enter') {
      e.preventDefault();
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
            onKeyDown={pressEnter}
          />
        </form>
        <select
          value={searchType}
          className={style.searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value='title'>제목</option>
          <option value='text'>내용</option>
          <option value='tag'>태그</option>
          <option value='nickname'>닉네임</option>
        </select>
        <button type='submit' className={style.SeachBtn} onClick={handleSubmit}>
          <img src='../../../assets/Community/Search.svg' />
        </button>
      </div>
      <div className={style.UserProfile}>
        <UserNav />
      </div>
    </div>
  );
};

export default SearchBar;
