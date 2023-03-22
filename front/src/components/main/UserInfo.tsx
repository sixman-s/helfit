import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';
import UserKcalChart from './util/UserKcalChart';
import UserWeightChart from './util/UserWeightChart';
import { useState, useEffect } from 'react';
import axios from 'axios';
import NonData from './atoms/NonData';

const UserInfo = ({ token }) => {
  const userData = JSON.parse(localStorage.getItem('UserInfo'));
  const [kcal, setKcal] = useState([]);
  const [weight, setWeight] = useState([]);
  const [kcalGoal, setkcalGoal] = useState(0);

  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const url = process.env.NEXT_PUBLIC_URL;
      axios
        .get(`${url}/api/v1/stat/calendar/${userData.userId}`, headers)
        .then((res) => {
          setKcal(res.data.body.data);
        })
        .catch((error) => console.log(error));
      axios
        .get(`${url}/api/v1/stat/physical`, headers)
        .then((res) => {
          setWeight(res.data.body.data);
        })
        .catch((error) => console.log(error));
      axios
        .get(`${url}/api/v1/calculate/${userData.userId}`, headers)
        .then((res) => {
          setkcalGoal(res.data.body.data.result);
        })
        .catch((error) => console.log(error));
    }
  }, [token]);

  return (
    <article>
      <header className={styled.header}>
        <h3 className={styled.h3}>
          <span className={styled.username}>{`${userData.nickname}님`}</span>
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
            <span className={styled.chartTitle}>평균 몸무게</span>
            <figcaption className={styled.chartCaption}>
              weight chart
            </figcaption>
          </div>
          <div className={styled.chartView}>
            {weight.length === 0 ? (
              <NonData link='/mypage' btn='Mypage' />
            ) : (
              <UserWeightChart data={weight} />
            )}
          </div>
        </figure>
        <figure className={styled.chart}>
          <div className={styled.chartInfo}>
            <span className={styled.chartTitle}>평균 칼로리</span>
            <figcaption className={styled.chartCaption}>kcal chart</figcaption>
          </div>
          <div className={styled.chartView}>
            {kcal.length === 0 ? (
              <NonData link='/calendar' btn='Calendar' />
            ) : (
              <UserKcalChart data={kcal} goal={kcalGoal} />
            )}
          </div>
        </figure>
      </div>
    </article>
  );
};

export default UserInfo;
