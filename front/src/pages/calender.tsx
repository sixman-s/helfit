import Layout from '@/components/MainLayout';
import styled from '../styles/calender/P_calender.module.css';
import CalenderContainer from '@/components/calender/CalenderContainer';

export default function Calender() {
  return (
    <Layout>
      <section className={styled.container}>
        <CalenderContainer />
      </section>
    </Layout>
  );
}
