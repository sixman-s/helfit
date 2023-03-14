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
    handleSubmit
    // formState: { errors }
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

  // 유효성 검사

  // const validateID = (userID: string) => {
  //   const constraint = /^[a-z0-9]{6,20}$/;
  //   if (!userID) {
  //     return 'userID cannot be empty';
  //   } else if (/\s/.test(userID)) {
  //     return 'userID cannot contain blank spaces';
  //   } else if (!constraint.test(userID)) {
  //     return 'userID must contain between 6 and 20 lowercase letters and numbers';
  //   }
  //   return '';
  // };

  // const validatePassword = (password: string) => {
  //   const constraint =
  //     /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  //   if (!password) {
  //     return 'Password cannot be empty';
  //   } else if (password.length < 8) {
  //     return 'Passwords must contain at least 8 characters';
  //   } else if (!constraint.test(password)) {
  //     return 'Please add at least 1 letter, 1 number, and 1 special character.';
  //   }
  //   return '';
  // };

  return (
    <>
      <div className={boxstyle.box}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={boxstyle.formID}>
            <h5>아이디</h5>
            <input
              type='text'
              {...register('userID', {
                required: true
                // validate: validateID
              })}
              className={boxstyle.login__form}
            />
            {/* {errors.userID && (
              <p style={{ color: 'red' }}>{errors.userID.message}</p>
            )} */}
          </div>
          <div className={boxstyle.formPassword}>
            <h5>비밀번호</h5>
            <input
              type='password'
              {...register('password', {
                required: true
                // validate: validatePassword
              })}
              className={boxstyle.login__form}
            />
            {/* {errors.password && (
              <p style={{ color: 'red' }}>{errors.password.message}</p>
            )} */}
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
