import login from '../styles/Login/P_Login.module.css';
import Layout from '@/components/MainLayout';
import LoginBox from '../components/Login/LoginBox';

const LoginP = () => {
  return (
    <>
      <Layout>
        <div className={login.layout}>
          <div className={login.svg_box}>
            <img
              className={login.svg_logo}
              src={'assets/LoginP/LoginPage.svg'}
            />
          </div>
          <div className={login.login_box}>
            <img className={login.logo} src={'assets/LoginP/logo.svg'} />
            <LoginBox />
          </div>
        </div>
      </Layout>
    </>
  );
};

export default LoginP;
