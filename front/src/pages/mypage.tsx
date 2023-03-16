import Layout from '@/components/MainLayout';
import CalorieInfo from '@/components/mypage/CalorieInfo';
import HealthInfo from '@/components/mypage/HealthInfo';
import MyList from '@/components/mypage/MyList';
import PersonalInfo from '@/components/mypage/PersonalInfo';
import axios from 'axios';
import { useEffect, useState } from 'react';
import MyPage from '../styles/mypage/P_mypage.module.css';

export default function Mypage() {
  const [detail, setDetail] = useState({});

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  // const loginInfo = JSON.parse(localStorage.UserInfo);
  // console.log(accessToken);
  useEffect(() => {
    axios
      .get(`${url}/api/v1/users`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
      .then((res) => {
        setDetail(res.data.body.data);
        // console.log(detail);
      })
      .catch((err) => console.log(err));
  }, []);

  // console.log(detail);
  return (
    <Layout>
      <div className={MyPage.myPageContainer}>
        <div className={MyPage.personalInfo}>
          <PersonalInfo detail={detail} />
        </div>
        <div className={MyPage.myList}>
          <MyList />
        </div>
        <div className={MyPage.healthInfo}>
          <HealthInfo detail={detail} />
        </div>
        <div className={MyPage.calorieInfo}>
          <CalorieInfo />
        </div>
      </div>
    </Layout>
  );
}
