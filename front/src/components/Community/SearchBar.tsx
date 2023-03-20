import React, { useState } from 'react';
import Router from 'next/router';
import style from '../../styles/Community/C_Community.module.css';
import UserNav from './C_Community/UserNav';

const SearchBar = () => {
  const [searchTerm, setSearchTerm] = useState('');

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
        <UserNav />
      </div>
    </div>
  );
};

export default SearchBar;
