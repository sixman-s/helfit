import Layout from '@/components/MainLayout';
import styled from '../styles/main/P_main.module.css';
import UserInfo from '@/components/main/UserInfo';
import CalendarInfo from '@/components/main/CalendarInfo';
import DietInfo from '@/components/main/DietInfo';
import CommunityInfo from '@/components/main/CommunityInfo';
import NonMembers from '@/components/main/NonMembers';
import VisitantInfo from '@/components/main/VisitantInfo';
import LoginServiceLayout from '@/components/main/LoginServiceLayout';
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
        {token ? (
          <UserInfo />
        ) : (
          <VisitantInfo>
            <NonMembers />
          </VisitantInfo>
        )}
        {token ? (
          <LoginServiceLayout title='Calendar' href='/calendar'>
            <CalendarInfo />
          </LoginServiceLayout>
        ) : (
          <LoginServiceLayout title='Calendar' href='/calendar'>
            <NonMembers />
          </LoginServiceLayout>
        )}
        {token ? (
          <LoginServiceLayout title={`Today's Diet`} href='/'>
            <DietInfo />
          </LoginServiceLayout>
        ) : (
          <LoginServiceLayout title={`Today's Diet`} href='/'>
            <NonMembers />
          </LoginServiceLayout>
        )}
        <CommunityInfo />
      </section>
    </Layout>
  );
}
