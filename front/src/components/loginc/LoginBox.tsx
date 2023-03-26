import boxstyle from '../../styles/Login/C_LoginBox.module.css';
import OAuthBox from './OAuth';
import Btn from './Buttons';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

const URL = process.env.NEXT_PUBLIC_URL;

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
      .post(`${URL}/api/v1/users/login`, {
        id: data.userID,
        password: data.password
      })
      .then((res) => {
        const accessToken = res.data.body.accessToken;
        localStorage.setItem('accessToken', accessToken);
        axios.defaults.headers.common[
          'Authorization'
        ] = `Bearer ${accessToken}`;

        axios
          .get(`${URL}/api/v1/users`)
          .then((res) => {
            const UserInfo = res.data.body.data;
            localStorage.setItem('UserInfo', JSON.stringify(UserInfo));
          })
          .then(() => router.push('/'))
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        if (error.response.status === 403) {
          alert('회원가입시 입력한 이메일에서 인증을 완료해주세요.');
        } else if (error.response.status === 400) {
          alert('아이디와 비밀번호가 일치하지 않습니다.');
        } else {
          console.log(error);
        }
      });
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={boxstyle.box}>
        <div className={boxstyle.formID}>
          <span className={boxstyle.h5}>아이디</span>
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
            <div className={boxstyle.errorMessage}>{errors.userID.message}</div>
          )}
        </div>
        <div className={boxstyle.formPassword}>
          <span className={boxstyle.h5}>비밀번호</span>
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
                  '대/소문자, 숫자, 특수문자가 1개 이상 포함되어야 합니다.'
              }
            })}
            className={boxstyle.login__form}
          />

          {errors.password && (
            <div className={boxstyle.errorMessage}>
              {errors.password.message}
            </div>
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
    </>
  );
};

export default LoginBox;
