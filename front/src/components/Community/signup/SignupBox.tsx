import style from '../../styles/Signup/C_SignupBox.module.css';
import OAuthBox from '../../login/OAuth';
import Btn from '../../login/Buttons';
import { useForm } from 'react-hook-form';

type SignupForm = {
  userID: string;
  email: string;
  password: string;
  name: string;
  birthdate: string;
  nickname: string;
};

const SignupBox = () => {
  const { register, handleSubmit } = useForm<SignupForm>();

  const onSubmit = (data: SignupForm) => console.log(data);

  return (
    <>
      <div className={style.container}>
        <div className={style.leftbox}>
          <div>
            <h5>아이디</h5>
            <input
              type='text'
              placeholder='아이디'
              {...register('userID', { required: true })}
              className={style.signup__form}
            />
          </div>
          <div>
            <h5>비밀번호</h5>
            <input
              type='password'
              placeholder='비밀번호'
              {...register('password', { required: true })}
              className={style.signup__form}
            />
          </div>
          <div>
            <h5>이메일</h5>
            <input
              type='text'
              placeholder='이메일'
              {...register('email', { required: true })}
              className={style.signup__form}
            />
          </div>
        </div>
        <div className={style.rightbox}>
          <img className={style.logo} src={'assets/LoginP/logo.svg'} />
          <div>
            <h5>생년월일</h5>
            <input
              type='text'
              placeholder='생년월일'
              {...register('birthdate', { required: true })}
              className={style.signup__form}
            />
            <h6 className={style.h6}>ex) 2000.01.01</h6>
          </div>
          <div>
            <h5>닉네임</h5>
            <input
              type='text'
              placeholder='닉네임'
              {...register('nickname', { required: true })}
              className={style.signup__form}
            />
          </div>
        </div>
        <div className={style.OAuthbox}>
          <OAuthBox />
        </div>
        <div className={style.signupButton}>
          <Btn
            className={style.button}
            text='가입하기'
            onClick={handleSubmit(onSubmit)}
          />
        </div>
      </div>
    </>
  );
};
export default SignupBox;
