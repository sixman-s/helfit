import styled from '../../styles/calender/C_calenderReadPop.module.css';
import { useState } from 'react';

const ReaderPop = ({ date, open, setOpen }) => {
  const [title, setTitle] = useState('');
  let year = date.getFullYear().toString();
  let month = (+date.getMonth() + 1).toString().padStart(2, '0');
  let day = date.getDate().toString().padStart(2, '0');

  return (
    <section className={open ? styled.article : styled.disable}>
      <header className={styled.header}>
        <button className={styled.closeBtn} onClick={() => setOpen(false)}>
          {'»'}
        </button>
      </header>
      <form className={styled.content}>
        <textarea
          className={styled.title}
          placeholder={'제목 없음'}
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <ul className={styled.infoUl}>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img className={styled.icon} src='/assets/lnb_calender_icn.svg' />
              <label className={styled.label}>날짜</label>
            </picture>
            <p className={styled.innerText}>{`${year}.${month}.${day}`}</p>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img className={styled.icon} src='/assets/cal_icn.svg' />
              <label className={styled.label}>칼로리</label>
            </picture>
            <p className={styled.innerText}>
              <input type='number' placeholder='0' />
              kcal
            </p>
          </li>
          <li className={`${styled.infoLi} ${styled.memoLi}`}>
            <textarea
              rows={20}
              placeholder='메모를 입력해주세요.'
              className={styled.memo}
            ></textarea>
          </li>
        </ul>
        <div className={styled.btnSet}>
          <button
            className={`${styled.btn} 
         ${styled.deletebtn}`}
          >
            Delete
          </button>
          <button
            className={`${styled.btn} 
         ${styled.submitbtn}`}
          >
            Submit
          </button>
        </div>
      </form>
    </section>
  );
};

export default ReaderPop;
