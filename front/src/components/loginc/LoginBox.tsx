import boxstyle from '../../styles/Login/C_LoginBox.module.css';
import OAuthBox from './OAuth';
import Btn from './Buttons';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

type LoginForm = {
  userID: string;
  password: string;
};

const LoginBox = () => {
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<LoginForm>();
  const router = useRouter();
  const SignuphandleClick = () => {
    router.push('/signup');
  };

  // 로그인 요청

  const onSubmit = (data: LoginForm) => {
    axios
      .post(
        'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/api/v1/users/login',
        {
          id: data.userID,
          password: data.password
        }
      )
      .then((res) => {
        localStorage.setItem('accessToken', res.data.body.accessToken);
        router.push('/');
      })
      .catch((error) => {
        console.log(error);
        alert('아이디 또는 비밀번호가 일치하지 않습니다.');
      });
  };

  return (
    <>
      <div className={boxstyle.box}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={boxstyle.formID}>
            <h5>아이디</h5>
            <input
              type='text'
              {...register('userID', {
                required: true,
                pattern: {
                  value: /^[a-z0-9]{6,20}$/,
                  message: '6~20글자 사이의 소문자 + 숫자의 조합이여야 합니다.'
                }
              })}
              className={boxstyle.login__form}
            />
            {errors.userID && (
              <div style={{ color: 'red' }}>{errors.userID.message}</div>
            )}
          </div>
          <div className={boxstyle.formPassword}>
            <h5>비밀번호</h5>
            <input
              type='password'
              {...register('password', {
                required: true,
                minLength: {
                  value: 8,
                  message: '8글자 이상이어야 합니다.'
                },
                pattern: {
                  value:
                    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
                  message:
                    '대문자, 소문자, 숫자, 특수문자가 1개 이상씩 포함되어야 합니다.'
                }
              })}
              className={boxstyle.login__form}
            />

            {errors.password && (
              <div style={{ color: 'red' }}>{errors.password.message}</div>
            )}
          </div>
          <div className={boxstyle.buttons}>
            <Btn
              className={boxstyle.button}
              text='회원가입'
              onClick={SignuphandleClick}
            />
            <Btn className={boxstyle.button} text='로그인' type='submit' />
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
