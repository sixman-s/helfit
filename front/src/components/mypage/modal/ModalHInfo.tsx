import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

import s from '../../../styles/mypage/M_ModalHInfo.module.css';

export interface detailProps {
  userId: number;
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

        const { birth, height, weight, gender, activityLevel, goal } = data;

        await axios
          .post(
            `${url}/api/v1/physical`,
            { birth, height, weight, gender },
            {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            }
          )
          .then((res) => {
            console.log(`health RES : ${res}`);
          })
          .catch((err) => console.log(err));

        await axios
          .post(
            `${url}/api/v1/calculate/${detail.userId}`,
            { activityLevel, goal },
            {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            }
          )
          .then((res) => {
            console.log(`calculate RES : ${res}`);
            router.reload();
          })
          .catch((err) => console.log(err));
      })}
    >
      <div className={s.inputDiv}>
        <div className={s.bottomInput}>
          <div className={s.hnw}>
            <label htmlFor='height'>키</label>
            <input
              type='text'
              id='height'
              className={s.hInfo}
              defaultValue={detail.height}
              {...register('height')}
            ></input>

            <label htmlFor='weight'>몸무게</label>
            <input
              type='text'
              id='weight'
              className={s.hInfo}
              defaultValue={detail.weight}
              {...register('weight')}
            ></input>

            <label htmlFor='birthDay'>생년월일</label>
            <input
              type='text'
              id='birthDay'
              className={s.info}
              defaultValue={detail.birth}
              {...register('birth')}
            />
          </div>
          <div id={s.genderDiv}>
            <span>성별</span>
            <div className={s.innerGenderDiv}>
              <div>
                <input
                  type='radio'
                  id='man'
                  value='MALE'
                  defaultChecked={detail.gender === 'MALE'}
                  {...register('gender')}
                />
                <label htmlFor='man'>남</label>
              </div>
              <div>
                <input
                  type='radio'
                  id='woman'
                  value='FEMALE'
                  defaultChecked={detail.gender === 'FEMALE'}
                  {...register('gender')}
                />
                <label htmlFor='woman'>여</label>
              </div>
            </div>
          </div>
        </div>
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
