import Layout from '@/components/MainLayout';
import styled from '../styles/main/P_main.module.css';
import UserInfo from '@/components/main/UserInfo';
import CalendarInfo from '@/components/main/CalendarInfo';
import DietInfo from '@/components/main/DietInfo';
import CommunityInfo from '@/components/main/CommunityInfo';
import Header from '@/components/main/atoms/Header';
import { useEffect, useState } from 'react';
import Intro from '@/components/main/Intro';

export default function Home() {
  const [token, setToken] = useState('');
  useEffect(() => {
    if (typeof window !== 'undefined') {
      setToken(localStorage.getItem('accessToken'));
    }
  }, []);

  return (
    <Layout>
      {token ? (
        <section className={styled.container}>
          <UserInfo token={token} />
          <Header title='Calendar' href='/calendar'>
            <CalendarInfo />
          </Header>
          <Header title={`Today's Diet`}>
            <DietInfo />
          </Header>
          <Header title={`Today's Member`} href='/community/oww'>
            <CommunityInfo token={token} />
          </Header>
        </section>
      ) : (
        <Intro />
      )}
    </Layout>
  );
}
