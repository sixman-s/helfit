import Layout from '@/components/MainLayout';
import CalorieInfo from '@/components/mypage/CalorieInfo';
import HealthInfo from '@/components/mypage/HealthInfo';
import MyList from '@/components/mypage/MyList';
import PersonalInfo from '@/components/mypage/PersonalInfo';
import axios from 'axios';
import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import MyPage from '../styles/mypage/P_mypage.module.css';

export interface userInfo1 {
  detail: {
    userId: number;
    id: string;
    email: string;
    nickname: string;
    profileImageUrl: string;
  };
}
export interface userInfo2 {
  hDetail: {
    birth: number;
    gender: string;
    height: number;
    weight: number;
  };
}

export interface userInfo3 {
  cDetail: {
    calculatorId: number;
    activityLevel: string;
    goal: string;
    result: number;
  };
}

export default function Mypage() {
  const [detail, setDetail] = useState<userInfo1['detail']>();
  const [hDetail, setHDetail] = useState<userInfo2['hDetail']>();
  const [cDetail, setCDetail] = useState<userInfo3['cDetail']>();
  // const router = useRouter();

  const [token, setToken] = useState<any>('');
  const url = process.env.NEXT_PUBLIC_URL;

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    setToken(accessToken);
    initMyPage(accessToken);
    // console.log('유주이펙' + detail);
  }, []);

  const initMyPage = async (token) => {
    try {
      const detail = await getUserInfo(token);
      // console.log('#####');
      const hDetail = await getPhysicalInfo({ token, detail });
      // console.log('@@@@@');
      const { userId } = detail;
      await getCalculateInfo({ userId, token, hDetail });
    } catch (e) {
      // console.log(e);
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
        // console.log('detail : ' + detail);
        // const userId = res.data.body.data.userId;
        const detail = res.data.body.data;
        // console.log('userId :' + userId);

        return detail;
      } catch (err) {
        // console.error(err);
      }
    }
  };

  const getPhysicalInfo = async ({ token, detail }) => {
    if (detail) {
      // console.log(token);
      // console.log('!!!!!!!');
      try {
        const res = await axios.get(`${url}/api/v1/physical/recent`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        // console.log('info : ' + res.data.body.data);
        setHDetail(res.data.body.data);
        return res.data.body.data;
        // console.log('hDetail : ' + JSON.stringify(hDetail));
      } catch (error) {
        console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>error');
        console.log(error);
        const today = new Date();
        const year = today.getFullYear() * 10000;
        const month = (today.getMonth() + 1) * 100;
        const date = today.getDate();
        console.log(year + month + date);
        setHDetail({
          birth: year + month + date,
          gender: 'test',
          height: 0,
          weight: 0
        });
      }
    } else {
      // console.log('detail undefind');
    }
  };

  const getCalculateInfo = async ({ userId, token, hDetail }) => {
    if (hDetail) {
      try {
        const res = await axios.get(`${url}/api/v1/calculate/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        // console.log(`계산기 요청 결과 : ${res.data.body.data}`);
        console.log(res.data.body.data);
        const {
          data: {
            body: { data: cDetailData }
          }
        } = res;
        // console.log('cDetailData : ' + cDetailData);
        setCDetail(cDetailData);
      } catch (error) {
        // console.log(error);
        const cDetailData = {};
        setCDetail({
          calculatorId: 0,
          activityLevel: '',
          goal: 'string',
          result: 0
        });
      }
    }
  };

  return (
    <Layout>
      <div className={MyPage.myPageContainer}>
        <div className={MyPage.personalInfo}>
          <PersonalInfo {...detail} />
        </div>
        <div className={MyPage.myList}>
          <MyList />
        </div>
        <div className={MyPage.healthInfo}>
          <HealthInfo detail={detail} hDetail={hDetail} cDetail={cDetail} />
        </div>
        <div className={MyPage.calorieInfo}></div>
      </div>
    </Layout>
  );
}
