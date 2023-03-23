import axios from 'axios';
import { useRouter } from 'next/router';
import { useForm } from 'react-hook-form';

import s from '../../../styles/mypage/M_ModalPInfo.module.css';

interface detailProps {
  userId: number;
  id: string;
  email: string;
  nickname: string;
  profileImageUrl: string;
  birth: number;
  gender: string;
  height: number;
  weight: number;
}

const ModalPInfo = ({ detail }: { detail: detailProps }) => {
  const { register, handleSubmit } = useForm();
  const router = useRouter();

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));
        console.log(data);
        axios
          .patch(`${url}/api/v1/users`, data, {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          })
          .then((res) => {
            {
              console.log(res);
              localStorage.removeItem('UserInfo');
              localStorage.setItem(
                'UserInfo',
                JSON.stringify(res.data.body.data)
              );
              router.reload();
            }
          })
          .catch((err) => console.log(err));
      })}
    >
      <div className={s.inputDiv}>
        <label htmlFor='nickName'>닉네임</label>
        <input
          type='text'
          id='nickName'
          className={s.info}
          defaultValue={detail.nickname}
          {...register('nickname')}
        ></input>

        <p className={s.inputP}>
          <button type='submit' id={s.submitBtn}>
            저장
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalPInfo;
