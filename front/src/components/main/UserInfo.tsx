import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';
import KcalChart from './util/KcalChart';

const UserInfo = ({ token }) => {
  const userData = JSON.parse(localStorage.getItem('UserInfo'));

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
            <span className={styled.chartTitle}>{userData.height}</span>
            <figcaption className={styled.chartCaption}>weight</figcaption>
          </div>
          <div className={styled.chartView}>몸무게 그래프</div>
        </figure>
        <figure className={styled.chart}>
          <div className={styled.chartInfo}>
            <span className={styled.chartTitle}>{'2000'}</span>
            <figcaption className={styled.chartCaption}>kcal</figcaption>
          </div>
          <div className={styled.chartView}>
            <KcalChart token={token} userId={userData.userId} />
          </div>
        </figure>
      </div>
    </article>
  );
};

export default UserInfo;
