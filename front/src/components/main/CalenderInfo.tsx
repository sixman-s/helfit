import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';

const CalenderInfo = () => {
  const username = '홍길동';
  return (
    <article>
      <header className={styled.header}>
        <h3 className={styled.h3}>
          <span className={styled.username}>{`${username}님`}</span>
          <br />
          핏한 하루 되셨나요?
        </h3>
        <Link href='/mypage'>
          <button className={styled.Btn}>my page</button>
        </Link>
      </header>
      <div className={styled.chartContainer}>
        <figure className={styled.chart}>
          <div className={styled.chartInfo}>
            <span className={styled.chartTitle}>{'72.8'}</span>
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

export default CalenderInfo;
