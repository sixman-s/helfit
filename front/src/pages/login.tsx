import login from '../styles/Login/P_Login.module.css';
import Layout from '@/components/MainLayout';
import LoginBox from '../components/loginc/LoginBox';

const LoginP = () => {
  return (
    <>
      <Layout>
        <div className={login.layout}>
          <div className={login.login_box}>
            <img
              className={login.leftImg}
              src={'../../assets/LoginP/로그인페이지1.png'}
            />
            <LoginBox />
          </div>
        </div>
      </Layout>
    </>
  );
};

export default LoginP;
