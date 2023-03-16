import Layout from '@/components/MainLayout';
import styled from '../styles/calendar/P_calendar.module.css';
import CalendarContainer from '@/components/calendar/CalendarContainer';

export default function calendar() {
  return (
    <Layout>
      <section className={styled.container}>
        <CalendarContainer />
      </section>
    </Layout>
  );
}
