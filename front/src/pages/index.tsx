import Layout from '@/components/MainLayout';
import styled from '../styles/main/P_main.module.css';
import UserInfo from '@/components/main/UserInfo';
import CalenderInfo from '@/components/main/CalenderInfo';
import FoodInfo from '@/components/main/FoodInfo';
import CommunityInfo from '@/components/main/CommunityInfo';

export default function Home() {
  return (
    <Layout>
      <section className={styled.container}>
        <UserInfo />
        <FoodInfo />
        <CalenderInfo />
        <CommunityInfo />
      </section>
    </Layout>
  );
}
