import axios from 'axios';
import style from '../../../styles/Signup/C_SignupBox.module.css';
import OAuthBox from '@/components/loginc/OAuth';
import Checkbox from './Checkbox';
import Btn from '@/components/loginc/Buttons';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

type SignupForm = {
  userID: string;
  email: string;
  password: string;
  name: string;
  birth: string;
  nickname: string;
};

const SignupBox = () => {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<SignupForm>();

  const onSubmit = (data: SignupForm) => {
    axios
      .post(
        'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/api/v1/users/signup',
        {
          id: data.userID,
          password: data.password,
          email: data.email,
          nickname: data.nickname,
          birth: data.birth
        }
      )
      .then((response) => {
        router.push('/login');
        console.log(response);
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div className={style.container}>
        <div className={style.leftbox}>
          {/* 아이디 입력칸 */}

          <div>
            <h5>아이디</h5>
            <input
              type='text'
              placeholder='아이디'
              {...register('userID', {
                required: true,
                pattern: {
                  value: /^[a-z0-9]{6,20}$/,
                  message: '6~20글자 사이의 소문자 + 숫자의 조합이여야 합니다.'
                }
              })}
              className={style.signup__form}
            />
            {errors.userID && (
              <div className={style.errormessage}>{errors.userID.message}</div>
            )}
          </div>

          {/* 비밀번호 입력칸 */}

          <div>
            <h5>비밀번호</h5>
            <input
              type='password'
              placeholder='비밀번호'
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
              className={style.signup__form}
            />
            {errors.password && (
              <div className={style.errormessage}>
                {errors.password.message}
              </div>
            )}
          </div>

          {/* 이메일 입력칸 */}

          <div>
            <h5>이메일</h5>
            <div>
              <input
                type='text'
                placeholder='이메일'
                {...register('email', {
                  required: true,
                  pattern: {
                    value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                    message: '올바른 이메일 형식으로 작성해주세요'
                  }
                })}
                className={style.signup__form}
              />
              {errors.email && (
                <div className={style.errormessage}>{errors.email.message}</div>
              )}
            </div>
          </div>
        </div>

        {/* 생년월일 입력칸 */}

        <div className={style.rightbox}>
          <img className={style.logo} src={'assets/LoginP/logo.svg'} />
          <div>
            <h5>생년월일</h5>
            <input
              type='text'
              placeholder='생년월일'
              {...register('birth', {
                required: true,
                pattern: {
                  value:
                    /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/,
                  message: '올바른 생년월일 8글자를 입력해주세요'
                }
              })}
              className={style.signup__form}
            />
            <h6 className={style.h6}>ex) 20000101</h6>
            {errors.birth && (
              <div className={style.errormessage}>{errors.birth.message}</div>
            )}
          </div>

          {/* 닉네임 입력칸 */}

          <div>
            <h5>닉네임</h5>
            <input
              type='text'
              placeholder='닉네임'
              {...register('nickname', {
                required: true,
                maxLength: 10
              })}
              className={style.signup__form}
            />
            {errors.nickname && errors.nickname.type === 'maxLength' && (
              <div className={style.errormessage}>
                닉네임은 10글자를 넘길 수 없습니다.
              </div>
            )}
          </div>
        </div>
        <div className={style.footer}>
          <div className={style.Checkbox}>
            <Checkbox />
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
      </div>
    </>
  );
};
export default SignupBox;
