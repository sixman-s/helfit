import Layout from '@/components/MainLayout';
import CalorieInfo from '@/components/mypage/CalorieInfo';
import HealthInfo from '@/components/mypage/HealthInfo';
import MyList from '@/components/mypage/MyList';
import PersonalInfo from '@/components/mypage/PersonalInfo';
import MyPage from '../styles/mypage/P_mypage.module.css'

export default function Mypage() {

  return (
    <Layout>
      <div className={MyPage.myPageContainer}>
        <div className={MyPage.personalInfo}><PersonalInfo /></div>
        <div className={MyPage.myList}><MyList /></div>
        <div className={MyPage.healthInfo}><HealthInfo /></div>
        <div className={MyPage.calorieInfo}><CalorieInfo /></div>
      </div>
    </Layout>
  );
}
