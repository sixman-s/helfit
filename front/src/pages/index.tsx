import Layout from '@/components/MainLayout';
import styled from '../styles/main/P_main.module.css';
import UserInfo from '@/components/main/UserInfo';
import CalendarInfo from '@/components/main/CalendarInfo';
import FoodInfo from '@/components/main/FoodInfo';
import CommunityInfo from '@/components/main/CommunityInfo';

export default function Home() {
  return (
    <Layout>
      <section className={styled.container}>
        <UserInfo />
        <CalendarInfo />
        <FoodInfo />
        <CommunityInfo />
      </section>
    </Layout>
  );
}
