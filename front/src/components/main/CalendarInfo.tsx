import Link from 'next/link';
import { useState } from 'react';
import layout from '../../styles/main/C_infoLayout.module.css';
import styled from '../../styles/main/C_calendarInfo.module.css';

const CalendarInfo = () => {
  const DateView = (current: Date) => {
    let year = current.getFullYear().toString();
    let month = (+current.getMonth() + 1).toString().padStart(2, '0');
    let day = current.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const [date, setDate] = useState(DateView(new Date()));

  const title = '제목을 입력해 주세요';
  const innerText = '테스트 텍스트입니다. ';
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Calendar</h2>
      </header>
      <form className={styled.form}>
        <input
          max={DateView(new Date())}
          className={styled.dateInput}
          type='date'
          data-placeholder='year-month-date'
          required
          onChange={(e) => setDate(e.target.value)}
        />
        <button className={styled.submitBtn}>Search</button>
      </form>
      <div>
        <ul className={styled.infoUl}>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='/assets/mainP/calendar_icn.svg'
              />
              <label className={styled.label}>날짜</label>
            </picture>
            <span className={styled.innerIext}>{date}</span>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img className={styled.icon} src='/assets/mainP/title_icn.svg' />
              <label className={styled.label}>타이틀</label>
            </picture>
            <span className={styled.innerIext}>{title}</span>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='/assets/mainP/context_icn.svg'
              />
              <label className={styled.label}>메모</label>
            </picture>
            <span className={styled.innerIext}>{innerText}</span>
          </li>
        </ul>
      </div>
    </article>
  );
};

export default CalendarInfo;
