import { useState } from 'react';
import styled from '../../styles/main/C_calendarInfo.module.css';
import { DateView } from '../calendar/utils/DateView';
import axios from 'axios';

const CalendarInfo = () => {
  const [date, setDate] = useState('0000-00-00');
  const [title, setTitle] = useState('제목 없음');
  const [calorie, setCalorie] = useState(0);
  const [innerText, setInnerText] = useState('내용 없음');
  const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/calendar?recodedAt=${date}`;

  const searchHandller = () => {
    if (typeof window !== 'undefined') {
      const token: string = localStorage.accessToken;
      if (token) {
        const headers = {
          headers: {
            Authorization: `Bearer ${token}`
          }
        };
        axios
          .get(url, headers)
          .then((res) => {
            const data = res.data.body.data;
            setTitle(data.title);
            setCalorie(data.kcal);
            setInnerText(data.content);
          })
          .catch((error) => {
            setTitle('제목 없음');
            setCalorie(0);
            setInnerText('내용 없음');
          });
      } else alert('로그인 후 이용해 주세요');
    }
  };

  return (
    <>
      <div className={styled.form}>
        <input
          max={DateView(new Date())}
          id='datepicker'
          className={styled.dateInput}
          type='date'
          data-placeholder='year-month-date'
          onChange={(e) => setDate(e.target.value)}
        />
        <button className={styled.submitBtn} onClick={() => searchHandller()}>
          Search
        </button>
      </div>
      <div>
        <ul className={styled.infoUl}>
          <li className={styled.infoLi}>
            <span className={styled.title}>{title}</span>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='../../assets/main/calendar_icn.svg'
              />
              <label className={styled.label}>날짜</label>
            </picture>
            <span className={styled.innerIext}>{date}</span>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='../../../assets/calendarP/cal_icn.svg'
              />
              <label className={styled.label}>칼로리</label>
            </picture>
            <p className={styled.innerIext}>
              {calorie}
              <span>{' kcal'}</span>
            </p>
          </li>
          <li className={styled.infoLi}>
            <picture className={styled.cate}>
              <img
                className={styled.icon}
                src='../../../assets/calendarP/cal_icn.svg'
              />
              <label className={styled.label}>메모</label>
            </picture>
            <span className={styled.innerIext}>{innerText}</span>
          </li>
          <li className={styled.infoLi}></li>
        </ul>
      </div>
    </>
  );
};

export default CalendarInfo;
