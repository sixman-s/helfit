import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';
import { useEffect, useState, useRef } from 'react';
import { axisBottom, axisRight, scaleBand, scaleLinear, select } from 'd3';
import axios from 'axios';

const UserInfo = ({ token }) => {
  const [name, setName] = useState('');
  // const [kcal, setKcal] = useState(null);
  // const svgRef = useRef(null);
  // const data = [21, 32, 53, 64, 75];
  // useEffect(() => {
  //   const svg = select(svgRef.current);
  // }, []);

  let userData = JSON.parse(localStorage.getItem('UserInfo'));
  console.log(userData);
  useEffect(() => {
    if (userData !== null && userData !== undefined) {
      setName(userData.nickname);
      if (token) {
        const headers = {
          headers: {
            Authorization: `Bearer ${token}`
          }
        };
        const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/stat/calendar/${userData.userId}`;
        axios
          .get(url, headers)
          .then((res) => {
            const data = res.data.body.data;
            // setKcal(data);
          })
          .catch((error) => console.log(error));
      }
    }
  }, [userData]);

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
            <span className={styled.chartTitle}>1800</span>
            <figcaption className={styled.chartCaption}>weight</figcaption>
          </div>
          {/* <svg className={styled.chartView} ref={svgRef}></svg> */}
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
