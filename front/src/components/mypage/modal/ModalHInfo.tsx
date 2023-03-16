import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

import s from '../../../styles/mypage/M_ModalHInfo.module.css';
import { createNoSubstitutionTemplateLiteral } from 'typescript';

export interface detailProps {
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

const ModalHInfo = ({ detail }: { detail: detailProps }) => {
  const { register, handleSubmit } = useForm();
  const router = useRouter();

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));
        if (!data.activityLevel && !data.goal) {
          alert('활동 정도와 운동 목적을 입력해주세요.');
        } else if (!data.activityLevel) {
          alert('활동 정도를 입력해주세요.');
        } else if (!data.goal) {
          alert('운동 목적을 입력해주세요.');
        } else {
          console.log(data);
          axios
            .post(`${url}/api/v1/calculate/${detail.userId}`, data, {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            })
            .then((data) => {
              {
                console.log(data);
                if (data.status === 200) {
                  alert('완료되었습니다.');
                  router.push('/mypage');
                }
              }
            })
            .catch((err) => console.log(err));
        }
      })}
    >
      <div className={s.inputDiv}>
        <label htmlFor='activity'>활동 정도</label>
        <select {...register('activityLevel')}>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='SEDENTARY'>거의 활동 없음</option>
          <option value='LIGHTLY_ACTIVE'>가벼운 활동</option>
          <option value='MODERATELY_ACTIVE'>적당한 활동</option>
          <option value='VERY_ACTIVE'>강도 높은 활동</option>
          <option value='EXTRA_ACTIVE'>격렬한 활동</option>
        </select>

        <label htmlFor='purpose'>운동 목적</label>
        <select {...register('goal')}>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='KEEP'>유지</option>
          <option value='DIET'>다이어트</option>
          <option value='BULK'>증량</option>
        </select>
        <p className={s.submitP}>
          <button type='submit' id={s.submitBtn}>
            Submit
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalHInfo;
