import Layout from '@/components/MainLayout';
import singup from '../styles/Signup/P_Signup.module.css';
import SignupBox from '@/components/signup/SignupBox';

const SignupP = () => {
  return (
    <Layout>
      <div className={singup.layout}>
        <SignupBox />
      </div>
    </Layout>
  );
};

export default SignupP;
