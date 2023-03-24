import axios from 'axios';
import { useRouter } from 'next/router';
import { useForm } from 'react-hook-form';

import s from '../../../styles/mypage/M_ModalQuit.module.css';

const ModalQuit = () => {
  const { register, handleSubmit } = useForm();
  const router = useRouter();

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));

        if (data.password) {
          axios
            .delete(`${url}/api/v1/users/withdraw`, {
              data,
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            })
            .then((res) => {
              localStorage.clear();
              router.push('/login');
            })
            .catch((err) => console.log(err));
        } else {
          alert('비밀번호를 입력해주세요.');
        }
      })}
    >
      <div className={s.inputDiv}>
        <label htmlFor='password'>비밀번호</label>
        <input
          type='password'
          id='password'
          className={s.info}
          {...register('password')}
        ></input>

        <p className={s.submitP}>
          <button type='submit' id={s.submitBtn}>
            탈퇴
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalQuit;
