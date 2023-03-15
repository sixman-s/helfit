import Link from 'next/link';
import { useState } from 'react';
import layout from '../../styles/main/C_infoLayout.module.css';
import styled from '../../styles/main/C_calendarInfo.module.css';
import { DateView } from '../calendar/utils/DateView';

const CalendarInfo = () => {
  const [date, setDate] = useState(DateView(new Date()));
  const calorie = 1000;
  const title = '제목을 입력해 주세요';
  const innerText =
    '테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다. 테스트 텍스트입니다.';

  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Calendar</h2>
        <Link className={layout.moreBtn} href='/calendar'>
          More
        </Link>
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
            <span className={styled.title}>{title}</span>
          </li>
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
              <img
                className={styled.icon}
                src='/assets/calendarP/cal_icn.svg'
              />
              <label className={styled.label}>칼로리</label>
            </picture>
            <span className={styled.innerIext}>{calorie}</span>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='/assets/calendarP/cal_icn.svg'
              />
              <label className={styled.label}>메모</label>
            </picture>
            <span className={styled.innerIext}>{innerText}</span>
          </li>
          <li className={styled.infoLi}></li>
        </ul>
      </div>
    </article>
  );
};

export default CalendarInfo;
