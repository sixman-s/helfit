import Layout from '@/components/MainLayout';
import styled from '../styles/main/P_main.module.css';
import UserInfo from '@/components/main/UserInfo';
import CalendarInfo from '@/components/main/CalendarInfo';
import DietInfo from '@/components/main/DietInfo';
import CommunityInfo from '@/components/main/CommunityInfo';
import SignupGuide from '@/components/main/atoms/SignupGuide';
import VisitantInfo from '@/components/main/NonUserInfo';
import Header from '@/components/main/atoms/Header';
import { useEffect, useState } from 'react';

export default function Home() {
  const [token, setToken] = useState('');
  useEffect(() => {
    if (typeof window !== 'undefined') {
      setToken(localStorage.getItem('accessToken'));
    }
  }, []);
  return (
    <Layout>
      <section className={styled.container}>
        {token ? <UserInfo token={token} /> : <VisitantInfo />}
        {token ? (
          <Header title='Calendar' href='/calendar'>
            <CalendarInfo />
          </Header>
        ) : (
          <Header title='Calendar' href='/calendar'>
            <SignupGuide />
          </Header>
        )}
        {token ? (
          <Header title={`Today's Diet`} href='/'>
            <DietInfo token={token} />
          </Header>
        ) : (
          <Header title={`Today's Diet`} href='/'>
            <SignupGuide />
          </Header>
        )}
        <CommunityInfo token={token} />
      </section>
    </Layout>
  );
}
