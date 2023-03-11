import boxstyle from '../../styles/Login/C_LoginBox.module.css';
import OAuthBox from './OAuth';
import Btn from './Buttons';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

type LoginForm = {
  userID: string;
  password: string;
};

const LoginBox = () => {
  const { register, handleSubmit } = useForm<LoginForm>();
  const router = useRouter();

  const onSubmit = async (data: LoginForm) => {
    // TODO: 로그인 로직 구현
    if (data.userID === 'user' && data.password === 'password') {
      router.push('/dashboard');
    } else {
      alert('아이디 또는 비밀번호가 일치하지 않습니다.');
    }
  };

  return (
    <>
      <div className={boxstyle.box}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={boxstyle.formID}>
            <h5>아이디</h5>
            <input
              type='text'
              {...register('userID', { required: true })}
              className={boxstyle.login__form}
            />
          </div>
          <div className={boxstyle.formPassword}>
            <h5>비밀번호</h5>
            <input
              type='password'
              {...register('password', { required: true })}
              className={boxstyle.login__form}
            />
          </div>
          <div className={boxstyle.buttons}>
            <Btn
              className={boxstyle.button}
              text='회원가입'
              onClick={() => console.log('Button clicked!')}
            />
            <button type='submit' className={boxstyle.button}>
              로그인
            </button>
          </div>
          <div className={boxstyle.OAuth}>
            <OAuthBox />
          </div>
        </form>
      </div>
    </>
  );
};
export default LoginBox;
