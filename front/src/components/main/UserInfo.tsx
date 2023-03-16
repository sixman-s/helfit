import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';
import { useEffect, useState } from 'react';

const UserInfo = () => {
  const [data, setData] = useState({});
  const [name, setName] = useState('');
  const [weight, setWeight] = useState(0);
  useEffect(() => {
    let userData = JSON.parse(localStorage.getItem('UserInfo'));
    if (userData !== null && userData !== undefined) {
      setData(userData);
      setName(userData.nickname);
      setWeight(userData.weight);
      console.log(userData.nickname);
    }
  }, []);

  return (
    <article>
      <header className={styled.header}>
        <h3 className={styled.h3}>
          <span className={styled.username}>{`${name}님`}</span>
          <br />
          핏한 하루 되셨나요?
        </h3>
        <Link href='/mypage'>
          <button className={styled.Btn}>Mypage</button>
        </Link>
      </header>
      <div className={styled.chartContainer}>
        <figure className={styled.chart}>
          <div className={styled.chartInfo}>
            <span className={styled.chartTitle}>{weight}</span>
            <figcaption className={styled.chartCaption}>weight</figcaption>
          </div>
          <div className={styled.chartView}>몸무게 그래프</div>
        </figure>
        <figure className={styled.chart}>
          <div className={styled.chartInfo}>
            <span className={styled.chartTitle}>{'1280'}</span>
            <figcaption className={styled.chartCaption}>kcal</figcaption>
          </div>
          <div className={styled.chartView}>칼로리 그래프</div>
        </figure>
      </div>
    </article>
  );
};

export default UserInfo;
