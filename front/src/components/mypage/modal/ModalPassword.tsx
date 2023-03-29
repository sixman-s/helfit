import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

import s from '../../../styles/mypage/M_ModalPassword.module.css';

interface PasswordForm {
  password: string;
  newPassword: string;
  passwordConfirm: string;
}

const ModalPassword = () => {
  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');
  const router = useRouter();

  const { register, watch, trigger, formState, handleSubmit } =
    useForm<PasswordForm>({
      mode: 'all'
    });

  const logout = () => {
    localStorage.clear();
    window.location.href = '/login';
  };

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));
        try {
          axios
            .patch(`${url}/api/v1/users/password`, data, {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            })
            .then((res) => {
              console.log(res);

              localStorage.clear();
              window.location.href = '/login';
            })
            .catch((err) => {
              console.log(err);
              alert('이전의 비밀번호와 같습니다.');
            });
        } catch (error: any) {
          console.log('비밀번호 변경 에러 발생');
          throw new Error(error);
        }
      })}
    >
      <div className={s.inputDiv}>
        <label htmlFor='checkPW'>새 비밀번호</label>
        <input
          type='password'
          id='checkPW'
          name='checkPW'
          placeholder='새 비밀번호를 입력해주세요.'
          {...register('password', {
            required: true,
            minLength: {
              value: 8,
              message: '8글자 이상이어야 합니다.'
            },
            pattern: {
              value:
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
              message: '대/소문자, 숫자, 특수문자가 1개 이상 포함되어야 합니다.'
            },
            onChange: () =>
              Boolean(formState.dirtyFields.passwordConfirm) &&
              trigger('passwordConfirm')
          })}
        />
        <p className={s.error}>{formState.errors.password?.message}</p>

        <label htmlFor='doubleCheckPW'>새 비밀번호 확인</label>
        <input
          type='password'
          id='doubleCheckPW'
          name='doubleCheckPW'
          placeholder='새 비밀번호를 한번 더 입력해주세요.'
          {...register('passwordConfirm', {
            validate: (value) =>
              value === watch('password') ? true : '비밀번호를 확인해주세요'
          })}
        />
        <p className={s.error}>{formState.errors.passwordConfirm?.message}</p>

        <p className={s.submitP}>
          <button id={s.submitBtn} disabled={!formState.isValid}>
            비밀번호 변경
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalPassword;
