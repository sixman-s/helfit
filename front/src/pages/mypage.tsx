import Layout from '@/components/MainLayout';
import CalorieInfo from '@/components/mypage/CalorieInfo';
import HealthInfo from '@/components/mypage/HealthInfo';
import MyList from '@/components/mypage/MyList';
import PersonalInfo from '@/components/mypage/PersonalInfo';
import axios from 'axios';
import { useEffect, useState } from 'react';
import MyPage from '../styles/mypage/P_mypage.module.css';

export default function Mypage() {
  const [detail, setDetail] = useState<object>({});
  const [hDetail, setHDetail] = useState({});
  const [cDetail, setCDetail] = useState();
  const [calorie, setCalorie] = useState<number>();

  const [token, setToken] = useState<any>('');
  console.log(localStorage.getItem('UserInfo'));
  const url = process.env.NEXT_PUBLIC_URL;

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    setToken(accessToken);
    initMyPage(accessToken);
  }, []);

  const initMyPage = async (token) => {
    try {
      const userId = await getUserInfo(token);
      await getPhysicalInfo(token);
      await getCalculateInfo({ userId, token });
    } catch (e) {
      console.log(e);
    }
  };

  const getUserInfo = async (token) => {
    if (token) {
      try {
        const res = await axios.get(`${url}/api/v1/users`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setDetail(res.data.body.data);
        console.log('info : ' + res.data.body.data);
        const userId = res.data.body.data.userId;
        console.log('userId :' + userId);

        return userId;
      } catch (err) {
        console.error(err);
      }
    }
  };

  const getPhysicalInfo = async (token) => {
    if (detail) {
      console.log(token);
      try {
        const res = await axios.get(`${url}/api/v1/physical/recent`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        console.log('info : ' + res.data.body.data);
        setHDetail(res.data.body.data);
        console.log('hDetail : ' + JSON.stringify(hDetail));
      } catch (error) {
        console.log(error);
      }
    }
  };

  const getCalculateInfo = async ({ userId, token }) => {
    if (hDetail) {
      console.log(userId);
      try {
        const res = await axios.get(`${url}/api/v1/calculate/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        console.log(`계산기 요청 결과 : ${res.data.body.data}`);
        console.log(res.data.body.data);
        const {
          data: {
            body: { data: cDetailData }
          }
        } = res;
        const { result: calorieData } = cDetailData;
        console.log('cDetailData : ' + cDetailData);
        console.log('calorieData : ' + calorieData);
        setCDetail(cDetailData);
        setCalorie(calorieData);
      } catch (error) {
        console.log(error);
        const cDetailData = undefined;
        const calorieData = 0;

        setCDetail(cDetailData);
        setCalorie(calorieData);
      }
    }
  };

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
          <HealthInfo detail={detail} hDetail={hDetail} cDetail={cDetail} />
        </div>
        <div className={MyPage.calorieInfo}>
          <CalorieInfo calorie={calorie} />
        </div>
      </div>
    </Layout>
  );
}
